package com.rpc.transport;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @描述
 * @创建人 Xiong Nie
 * @创建时间 2021/12/1
 * @修改人和其它信息
 */
public interface RequestHandler {
    void onRequest(InputStream recive, OutputStream toResp);
}
