import com.rpc.*;
import com.rpc.codec.Decoder;
import com.rpc.codec.Encoder;
import com.rpc.common.utils.ReflectionUtils;
import com.rpc.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @描述
 * @创建人 Xiong Nie
 * @创建时间 2021/12/2
 * @修改人和其它信息
 */
@Slf4j
public class RemoteInvoker implements InvocationHandler {
    private Class clazz;
    private NettyClient nettyClient;
    public RemoteInvoker(Class clazz, NettyClient nettyClient){
        this.clazz=clazz;
        this.nettyClient=nettyClient;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();
        String interfaceName=method.getDeclaringClass().getName();
        System.out.println(interfaceName);
        request.setInterfaceName(interfaceName);
        request.setMethodName(method.getName());
        request.setParameters(args);
        request.setParamTypes(method.getParameterTypes());
        Object response= nettyClient.sendRequest(request);
        return ((RpcResponse)response).getData();
    }
}
