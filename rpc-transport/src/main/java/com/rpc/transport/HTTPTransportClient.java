package com.rpc.transport;


import com.rpc.Peer;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @描述
 * @创建人 Xiong Nie
 * @创建时间 2021/12/1
 * @修改人和其它信息
 */
public class HTTPTransportClient implements TransportClient {
    private String url;

    @Override
    public void connect(Peer peer) {
        this.url = " http://" + peer.getHost() + ":" + peer.getPost();

    }

    @Override
    public InputStream write(InputStream data) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.connect();
            IOUtils.copy(data, httpURLConnection.getOutputStream());
            int resultCode = httpURLConnection.getResponseCode();
            if (resultCode == httpURLConnection.HTTP_OK) {
                return httpURLConnection.getInputStream();
            } else {
                return httpURLConnection.getErrorStream();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() {

    }
}
