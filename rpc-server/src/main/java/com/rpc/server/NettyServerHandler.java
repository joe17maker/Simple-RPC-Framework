package com.rpc.server;


import com.rpc.RpcRequest;
import com.rpc.RpcResponse;
import com.rpc.server.RequestHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;


/**
 * @描述
 * @创建人 Xiong Nie
 * @创建时间 2021/12/5
 * @修改人和其它信息
 */
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {
    private final RequestHandler requestHandler;

    public NettyServerHandler(){
        requestHandler=new RequestHandler();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request) throws Exception {
        log.info("server recive request:{}",request);
        Object result= requestHandler.handle(request);
        RpcResponse rpcResponse=RpcResponse.success(result,request.getRequestId());
        log.info("server send response {}",rpcResponse);
        ctx.writeAndFlush(rpcResponse);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("exception occurs when handling{}",cause);
        cause.printStackTrace();
        ctx.close();
    }


}
