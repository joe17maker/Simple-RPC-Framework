import com.rpc.Peer;
import com.rpc.codec.Decoder;
import com.rpc.codec.Encoder;
import com.rpc.codec.JSONDecoder;
import com.rpc.codec.JSONEncoder;
import com.rpc.transport.HTTPTransportClient;
import com.rpc.transport.TransportClient;
import lombok.Data;

import java.util.Arrays;
import java.util.List;


/**
 * @描述
 * @创建人 Xiong Nie
 * @创建时间 2021/12/2
 * @修改人和其它信息
 */
@Data
public class RpcClientConfig {
    private Class<? extends TransportClient> transportClass = HTTPTransportClient.class;

    private Class<? extends Encoder> encoderClass = JSONEncoder.class;

    private Class<? extends Decoder> decoderClass = JSONDecoder.class;

    private Class<? extends TransportSelector> selectClass = RandomTransportSelector.class;

    private int connectCount = 1;

    private List<Peer> servers = Arrays.asList(
            new Peer("192.168.201.73", 3000)
    );
}
