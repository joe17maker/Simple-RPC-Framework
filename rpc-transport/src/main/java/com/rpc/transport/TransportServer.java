package com.rpc.transport;

import org.eclipse.jetty.http.HttpParser;

/**
 * @描述
 * @创建人 Xiong Nie
 * @创建时间 2021/12/1
 * @修改人和其它信息
 */
public interface TransportServer {
    void init(int port, RequestHandler handler);

    void start();

    void stop();
}
