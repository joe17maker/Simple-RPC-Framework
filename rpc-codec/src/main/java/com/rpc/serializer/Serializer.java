package com.rpc.serializer;

/**
 * @author Xiong Nie
 * @date 2021/12/9
 */
public interface Serializer {
    byte[] serialize(Object obj);

    <T> T deserialize(byte[] bytes, Class<T> clazz);

}
