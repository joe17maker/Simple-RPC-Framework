import com.rpc.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

/**
 * @author Xiong Nie
 * @date 2021/12/7
 */
@Slf4j
public class NettyClienthandler extends SimpleChannelInboundHandler<RpcResponse> {
    private UnprocessedRequest unprocessedRequest=new UnprocessedRequest();


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse rpcResponse) throws Exception {
        log.info("recive response:{}",rpcResponse);
        unprocessedRequest.complete(rpcResponse);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("exceptions when handling response{}",cause);
        ctx.close();
    }
}
