package com.rpc.server;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Xiong Nie
 * @date 2021/12/7
 */
@Slf4j
public class ServiceProvider {
    private static final Map<String,Object> serviceMap=new ConcurrentHashMap<>();

    public static <T> void addServiceProvider(String serviceName,T service){
        serviceMap.put(serviceName,service);
        log.info("record {} provide service for {}",service,serviceName);
    }

    public static Object getServiceProvider(String serviceName){
        Object service=serviceMap.get(serviceName);
        if(service==null){
            throw new RuntimeException("service not found");
        }
        return service;
    }
}
