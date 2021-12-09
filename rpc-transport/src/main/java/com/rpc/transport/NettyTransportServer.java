package com.rpc.transport;

import com.rpc.Request;

/**
 * @描述
 * @创建人 Xiong Nie
 * @创建时间 2021/12/5
 * @修改人和其它信息
 */
public class NettyTransportServer implements TransportServer{
    private int port;
    private RequestHandler handler;
    @Override
    public void init(int port, RequestHandler handler) {
        this.port=port;
        this.handler=handler;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
