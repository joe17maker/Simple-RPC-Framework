package com.rpc.server;

import com.rpc.codec.Decoder;
import com.rpc.codec.Encoder;
import com.rpc.codec.JSONDecoder;
import com.rpc.codec.JSONEncoder;
import com.rpc.transport.HTTPTransportServer;
import com.rpc.transport.TransportServer;
import lombok.Data;

/**
 * @描述
 * @创建人 Xiong Nie
 * @创建时间 2021/12/2
 * @修改人和其它信息
 */
@Data
public class RpcServerConfig {
    private Class<? extends TransportServer> transportClass= HTTPTransportServer.class;
    private Class<? extends Encoder> encoderClass= JSONEncoder.class;
    private Class<? extends Decoder> deocderClass= JSONDecoder.class;

}
