import java.util.Random;

/**
 * @描述
 * @创建人 Xiong Nie
 * @创建时间 2021/12/2
 * @修改人和其它信息
 */
public class Client {
    public static void main(String[] args) {
        NettyClient nettyClient=new NettyClient("127.0.0.1",3000);
        CalculateService calculateService=nettyClient.getProxy(CalculateService.class);
        Random random=new Random();
        for(int i=0;i<10;i++){
            System.out.println(calculateService.def(random.nextInt(10),random.nextInt(10)));
        }
    }
}
