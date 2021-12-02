package com.rpc.codec;

/**
 * @描述
 * @创建人 Xiong Nie
 * @创建时间 2021/12/1
 * @修改人和其它信息
 */
public interface Decoder {
    <T> T decode(byte[] bytes, Class<T> clazz);

}
