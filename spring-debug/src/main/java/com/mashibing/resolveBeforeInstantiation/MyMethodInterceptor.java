package com.mashibing.resolveBeforeInstantiation;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 使用代理类 来生成Bean 对象
 */
public class MyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("目标方法执行之前：" + method);
        Object o1 = methodProxy.invokeSuper(o, objects);
        System.out.println("目标方法执行之后：" + method);
        return o1;
    }
}
