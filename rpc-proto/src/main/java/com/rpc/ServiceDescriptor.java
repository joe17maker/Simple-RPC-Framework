package com.rpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @描述 表示服务
 * @创建人 Xiong Nie
 * @创建时间 2021/11/30
 * @修改人和其它信息
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ServiceDescriptor {
    private String clazz;
    private String method;
    private String returnType;
    private String[] parameterTypes;

    public static ServiceDescriptor from(Class clazz, Method method) {
        ServiceDescriptor serviceDescriptor = new ServiceDescriptor();
        serviceDescriptor.clazz = clazz.getName();
        serviceDescriptor.method = method.getName();
        serviceDescriptor.returnType = method.getReturnType().getName();
        String[] para = new String[method.getParameterCount()];
        int i = 0;
        for (Class paraClass : method.getParameterTypes()) {
            para[i++] = paraClass.getName();

        }
        return serviceDescriptor;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ServiceDescriptor other = (ServiceDescriptor) obj;
        return (this.toString().equals(other.toString()));
    }

    @Override
    public String toString() {
        return "clazz=" + clazz
                + "method=" + method
                + "returnType=" + returnType
                + "parameterTypes=" + Arrays.toString(parameterTypes);
    }
}
