/**
 * @描述
 * @创建人 Xiong Nie
 * @创建时间 2021/12/2
 * @修改人和其它信息
 */
public class Calculate implements CalculateService {
    @Override
    public int add(int a, int b) {
        return a+b;
    }

    @Override
    public int def(int a, int b) {
        return a-b;
    }
}
