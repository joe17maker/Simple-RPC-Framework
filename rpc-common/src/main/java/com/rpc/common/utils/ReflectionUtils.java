package com.rpc.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @描述
 * @创建人 Xiong Nie
 * @创建时间 2021/11/30
 * @修改人和其它信息
 */
public class ReflectionUtils {
    /**
     *@描述 
     *@参数  要实例化类型对应的class
     *@返回值  该类型的对象
     *@创建人  xiong Nie
     *@创建时间  2021/11/30
     */
    public static <T> T newInstance(Class<T> clazz){
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
//获取当前类的所有公共方法，不包括父类
    public static Method[] getPublicMethods(Class clazz){
        Method[] methods = clazz.getDeclaredMethods();
        List<Method> methodList = new ArrayList<>();
        for(Method m:methods){
            if(Modifier.isPublic(m.getModifiers())){
                methodList.add(m);
            }
        }
        return methodList.toArray(new Method[0]);
    }
    public static Object invoke(Object obj, Method method, Object... args){
        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}
