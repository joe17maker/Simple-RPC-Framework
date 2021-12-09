import com.rpc.RpcResponse;
import com.rpc.common.enumeration.ResponseCode;
import com.rpc.common.utils.ReflectionUtils;
import com.rpc.server.NettyServer;
import com.rpc.server.RpcServer;
import com.rpc.server.RpcServerConfig;
import com.rpc.server.ServiceProvider;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @描述
 * @创建人 Xiong Nie
 * @创建时间 2021/12/2
 * @修改人和其它信息
 */
@Slf4j
public class Server {

    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer("127.0.0.1", 3000);
        nettyServer.registerService(CalculateService.class, new Calculate());
        System.out.println(Arrays.toString(Calculate.class.getMethods()));
        nettyServer.start();
    }
}
