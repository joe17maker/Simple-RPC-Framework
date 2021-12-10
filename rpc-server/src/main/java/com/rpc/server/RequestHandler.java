package com.rpc.server;

import com.rpc.RpcRequest;
import com.rpc.RpcResponse;
import com.rpc.common.enumeration.ResponseCode;
import com.rpc.common.utils.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @author Xiong Nie
 * @date 2021/12/6
 */
@Slf4j
public class RequestHandler {
    private static final ServiceProvider serviceProvider=new ServiceProvider();
    public Object handle(RpcRequest rpcRequest){
        Object target=serviceProvider.getServiceProvider(rpcRequest.getInterfaceName());
        Method method= null;
        Object result=null;
        try {
            method = target.getClass().getMethod(rpcRequest.getMethodName(),rpcRequest.getParamTypes());
            result=ReflectionUtils.invoke(target,method,rpcRequest.getParameters());
            log.info("server invoke method [{}] success",method);
        } catch (NoSuchMethodException e) {
            log.error("server invoke method fail");
            return RpcResponse.fail(ResponseCode.METHOD_NOT_FOUND,rpcRequest.getRequestId());
        }
        return result;
    }
}
