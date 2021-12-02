package com.rpc.codec;


import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @描述
 * @创建人 Xiong Nie
 * @创建时间 2021/12/1
 * @修改人和其它信息
 */
public class JSONEncoderTest {


    @Test
    public void encode() {
        Encoder encoder=new JSONEncoder();
        TestClass t=new TestClass();
        t.setB(23);
        t.setName("niexiong");
        byte[] bytes=encoder.encode(t);
    }
}
