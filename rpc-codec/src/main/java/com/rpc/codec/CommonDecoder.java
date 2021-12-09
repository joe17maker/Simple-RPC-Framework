package com.rpc.codec;

import com.rpc.RpcRequest;
import com.rpc.RpcResponse;
import com.rpc.common.enumeration.PackageType;
import com.rpc.serializer.KryoSerializer;
import com.rpc.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author Xiong Nie
 * @date 2021/12/6
 */
@Slf4j
public class CommonDecoder extends ReplayingDecoder {
    private static final int MAGIC_NUMBER = 0xAFAFBABA;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        log.info("decode");
        int magic = in.readInt();
        if (magic != MAGIC_NUMBER) {
            log.error("protocol unrecongnized:{}", magic);
            throw new Exception();
        }
        int packageType = in.readInt();
        Class clazz = null;
        if (packageType == PackageType.REQUEST_PACKAGE.getType()) {
            clazz = RpcRequest.class;
        } else if (packageType == PackageType.RESPONSE_PACKAGE.getType()) {
            clazz = RpcResponse.class;
        } else {
            log.error("package unrecogniezed");
        }
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        Serializer serializer = new KryoSerializer();
        Object obj = serializer.deserialize(bytes, clazz);
        out.add(obj);
        log.info("decode complete");
    }
}
