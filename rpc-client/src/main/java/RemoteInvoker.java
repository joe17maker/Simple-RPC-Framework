import com.rpc.Request;
import com.rpc.Response;
import com.rpc.ServiceDescriptor;
import com.rpc.codec.Decoder;
import com.rpc.codec.Encoder;
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
    private Encoder encoder;
    private Decoder decoder;
    private TransportSelector selector;

    public RemoteInvoker(Class clazz,
                         Encoder encoder,
                         Decoder decoder,
                         TransportSelector selector) {
        this.clazz=clazz;
        this.decoder = decoder;
        this.encoder = encoder;
        this.selector = selector;

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Request request = new Request();
        request.setService(ServiceDescriptor.from(clazz, method));
        request.setParameters(args);
        Response response = invokeRemote(request);
        if (response == null || response.getCode() != 0) {
            throw new IllegalStateException("fail to invoke remote:" + response);
        }
        return response.getData();
    }

    private Response invokeRemote(Request request) {
        Response response=null;
        TransportClient client = null;
        try {
            client = selector.select();
            byte[] outBytes = encoder.encode(request);
            InputStream revice = client.write(new ByteArrayInputStream(outBytes));
            byte[] inBytes = IOUtils.readFully(revice, revice.available());
            response=decoder.decode(inBytes,Response.class);

        } catch (IOException e) {
            log.warn(e.getMessage(),e);
            response =new Response();
            response.setCode(1);
            response.setMessage("RpcClient got error:"+
                    e.getClass()+
                    e.getMessage());
        } finally {
            if (client != null)
                selector.release(client);
        }
        return response;
    }
}
