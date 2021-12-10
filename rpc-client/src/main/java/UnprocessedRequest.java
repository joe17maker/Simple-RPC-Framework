import com.rpc.RpcResponse;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Xiong Nie
 * @date 2021/12/10
 */
public class UnprocessedRequest {
    public static Map<String, CompletableFuture<RpcResponse>> unprocessedResponseFuture = new ConcurrentHashMap<>();

    public void put(String requestId, CompletableFuture<RpcResponse> future) {
        unprocessedResponseFuture.put(requestId, future);
    }

    public CompletableFuture<RpcResponse> remove(String requestId) {
        return unprocessedResponseFuture.remove(requestId);
    }

    public CompletableFuture<RpcResponse> complete(RpcResponse rpcResponse) {
        CompletableFuture<RpcResponse> future = unprocessedResponseFuture.remove(rpcResponse.getRequestId());
        future.complete(rpcResponse);
        return future;
    }
}
