import com.rpc.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @描述
 * @创建人 Xiong Nie
 * @创建时间 2021/12/2
 * @修改人和其它信息
 */
@Slf4j
public class RpcClientProxy implements InvocationHandler {
    private NettyClient nettyClient;

    public RpcClientProxy(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }

    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class[]{clazz},
                this
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        RpcRequest request = new RpcRequest(method.getDeclaringClass().getName(),
                method.getName(),
                args,
                method.getParameterTypes(),
                UUID.randomUUID().toString());
        log.info("invoke method {}:{}", method.getDeclaringClass().getName(), method.getName());
        CompletableFuture<RpcResponse> responseCompletableFuture = nettyClient.sendRequest(request);
        RpcResponse rpcResponse = null;
        try {
            rpcResponse = responseCompletableFuture.get();
        } catch (InterruptedException e) {
            log.error("Failed to invoke {}", e.getMessage());
        } catch (ExecutionException e) {
            log.error("Failed to invoke {}", e.getMessage());
        }
        return rpcResponse.getData();
    }
}
