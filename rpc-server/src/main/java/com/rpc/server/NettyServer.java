package com.rpc.server;

import com.rpc.codec.CommonDecoder;
import com.rpc.codec.CommonEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @描述
 * @创建人 Xiong Nie
 * @创建时间 2021/12/5
 * @修改人和其它信息
 */
@Slf4j
public class NettyServer {
    private String host;
    private int port;

    public NettyServer(String host, int port){
        this.host=host;
        this.port=port;
    }
    public void start() {
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childHandler(new ChannelInitializer<SocketChannel>(){
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline channelPipeline=socketChannel.pipeline();
                            channelPipeline.addLast(new CommonEncoder())
                                            .addLast(new CommonDecoder())
                                            .addLast(new NettyServerHandler());
                        }
                    });
            ChannelFuture channelFuture=serverBootstrap.bind(host,port).sync();
            log.info("server start");
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            log.error("start server fail",e);
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
    public <T> void registerService(Class<T> clazz,T instance){
        ServiceProvider.addServiceProvider(clazz.getName(),instance);
    }

    public void stop() {

    }
}
