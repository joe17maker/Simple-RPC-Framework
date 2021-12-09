package com.rpc.codec;

import com.rpc.RpcRequest;
import com.rpc.RpcResponse;
import com.rpc.common.enumeration.PackageType;
import com.rpc.serializer.KryoSerializer;
import com.rpc.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Xiong Nie
 * @date 2021/12/6
 */
@Slf4j
public class CommonEncoder extends MessageToByteEncoder {
    private static final int MAGIC_NUMBER=0xAFAFBABA;

    @Override
    protected void encode(ChannelHandlerContext ctx, Object obj, ByteBuf out) throws Exception {
        log.info("encode");
        out.writeInt(MAGIC_NUMBER);
        if(obj instanceof RpcRequest){
            out.writeInt(PackageType.REQUEST_PACKAGE.getType());
        }else if(obj instanceof RpcResponse){
            out.writeInt(PackageType.RESPONSE_PACKAGE.getType());
        }else {
            log.error("package unrecognized");
        }
        Serializer serializer=new KryoSerializer();
        byte[] bytes=serializer.serialize(obj);
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }
}
