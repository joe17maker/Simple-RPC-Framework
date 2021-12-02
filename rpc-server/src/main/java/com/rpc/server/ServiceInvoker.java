package com.rpc.server;

import com.rpc.Request;
import com.rpc.common.utils.ReflectionUtils;

/**
 * @描述
 * @创建人 Xiong Nie
 * @创建时间 2021/12/2
 * @修改人和其它信息
 */
public class ServiceInvoker {
    public Object invoke(ServiceInstance serviceInstance, Request request){
        return ReflectionUtils.invoke(serviceInstance.getTarget(),serviceInstance.getMethod(),request.getParameters());
    }
}
