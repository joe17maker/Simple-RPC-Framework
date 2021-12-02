import com.rpc.Peer;
import com.rpc.common.utils.ReflectionUtils;
import com.rpc.transport.TransportClient;
import com.rpc.transport.TransportServer;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @描述
 * @创建人 Xiong Nie
 * @创建时间 2021/12/2
 * @修改人和其它信息
 */
public class RandomTransportSelector implements TransportSelector {
    private List<TransportClient> clients;

    public RandomTransportSelector() {
        clients=new LinkedList<>();
    }

    @Override
    public synchronized void init(List<Peer> peers, int count, Class<? extends TransportClient> clazz) {
        count = Math.max(count, 1);
        for (Peer peer : peers) {
            for (int i = 0; i < count; i++) {
                TransportClient client = ReflectionUtils.newInstance(clazz);
                client.connect(peer);
                clients.add(client);
            }
        }
    }

    @Override
    public synchronized TransportClient select() {
        int i = new Random().nextInt(clients.size());
        return clients.remove(i);
    }

    @Override
    public synchronized void release(TransportClient client) {
        clients.add(client);

    }

    @Override
    public synchronized void close() {
        for (TransportClient client : clients) {
            client.close();
        }
        clients.clear();

    }
}
