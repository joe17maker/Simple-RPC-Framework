package com.rpc.server;

import com.rpc.Request;
import com.rpc.ServiceDescriptor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @描述
 * @创建人 Xiong Nie
 * @创建时间 2021/12/2
 * @修改人和其它信息
 */
public class ServiceManagerTest {
    ServiceManager sm;

    @Before
    public void init(){
        sm=new ServiceManager();
    }

    @Test
    public void register() {
        TestInterface bean=new TestClass();
        sm.register(TestInterface.class,bean);
    }

    @Test
    public void lookup() {
        try {
            register();
            Request request=new Request(ServiceDescriptor.from(TestInterface.class,TestInterface.class.getMethod("hello")),null);
            ServiceInstance sis=sm.lookup(request);
            assertNotNull(sis);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}