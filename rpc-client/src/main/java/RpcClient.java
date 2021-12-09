import com.rpc.RpcRequest;
import com.rpc.RpcResponse;
import com.rpc.codec.Decoder;
import com.rpc.codec.Encoder;
import com.rpc.common.utils.ReflectionUtils;

import java.lang.reflect.Proxy;

/**
 * @描述
 * @创建人 Xiong Nie
 * @创建时间 2021/12/2
 * @修改人和其它信息
 */
public interface RpcClient {
    RpcResponse sendRequest(RpcRequest rpcRequest);
}
