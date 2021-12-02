package com.rpc.codec;

import com.alibaba.fastjson.JSON;

/**
 * @描述
 * @创建人 Xiong Nie
 * @创建时间 2021/12/1
 * @修改人和其它信息
 */
public class JSONEncoder implements Encoder{
    @Override
    public byte[] encode(Object object) {
        return JSON.toJSONBytes(object);
    }
}
