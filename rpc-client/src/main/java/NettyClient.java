import com.rpc.RpcRequest;
import com.rpc.RpcResponse;
import com.rpc.codec.CommonDecoder;
import com.rpc.codec.CommonEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;

/**
 * @author Xiong Nie
 * @date 2021/12/7
 */
@Slf4j
public class NettyClient implements RpcClient{
    private ChannelFuture channelFuture;
    private String host;
    private int port;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private void connect() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap boostrap = new Bootstrap();
        try {
            boostrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new CommonDecoder())
                                    .addLast(new CommonEncoder())
                                    .addLast(new NettyClienthandler());
                        }
                    });
            channelFuture = boostrap.connect(host, port).sync();
            log.info("Connect to server {}:{}",host,port);
        } catch (InterruptedException e) {
            log.error("Failed to start client{}", e);
        }
    }

    @Override
    public RpcResponse sendRequest(RpcRequest rpcRequest) {
        try {
            Channel channel = channelFuture.channel();
            if(channel != null) {
                channel.writeAndFlush(rpcRequest).addListener(future1 -> {
                    if(future1.isSuccess()) {
                        log.info(String.format("send request: %s", rpcRequest.toString()));
                    } else {
                        log.error("Failed to send request: ", future1.cause());
                    }
                });
                channel.closeFuture().sync();
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
                RpcResponse rpcResponse = channel.attr(key).get();
                return rpcResponse;
            }
        } catch (InterruptedException e) {
            log.error("Failed to send request: ", e);
        }
        return null;
    }

    public <T> T getProxy(Class<T> clazz){
        this.connect();
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class[]{clazz},
                new RemoteInvoker(clazz,this)
        );
    }
}
