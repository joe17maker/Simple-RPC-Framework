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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author Xiong Nie
 * @date 2021/12/7
 */
@Slf4j
public class NettyClient implements RpcClient {
    private EventLoopGroup group;
    private Bootstrap boostrap;
    private ChannelFuture channelFuture;
    private String host;
    private int port;
    private UnprocessedRequest unprocessedResult=new UnprocessedRequest();

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.connect();
    }

    private void connect() {
        group = new NioEventLoopGroup();
        boostrap = new Bootstrap();
        try {
            boostrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new CommonEncoder())
                                    .addLast(new CommonDecoder())
                                    .addLast(new NettyClienthandler());
                        }
                    });
            channelFuture = boostrap.connect(host, port).sync();
            log.info("Connect to server {}:{}", host, port);
//            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("Failed to start client{}", e.getMessage());
        }
//        } finally {
//            group.shutdownGracefully().sync();
//            log.info("Client release resource");
//        }
    }

    @Override
    public CompletableFuture<RpcResponse> sendRequest(RpcRequest rpcRequest) {
        Channel channel = channelFuture.channel();
        CompletableFuture<RpcResponse> responseFuture = new CompletableFuture<>();
        unprocessedResult.put(rpcRequest.getRequestId(),responseFuture);
        channel.writeAndFlush(rpcRequest).addListener(future1 -> {
            if (future1.isSuccess()) {
                log.info("send request: {}", rpcRequest  );
            } else {
                log.error("Failed to send request: ", future1.cause());
            }
        });
        return responseFuture;
    }

    public void close() throws InterruptedException {
        group.shutdownGracefully().sync();
    }


}
