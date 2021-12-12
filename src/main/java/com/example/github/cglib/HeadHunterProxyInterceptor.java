package com.example.github.cglib;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
@Slf4j
public class HeadHunterProxyInterceptor implements MethodInterceptor {
    private Object object;

    public HeadHunterProxyInterceptor(Object object) {
        this.object = object;
    }
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if (method.getName().equals("interview")) {
            log.info("你是谁？来干什么？");
            //反射
            method.invoke(object);
            log.info("那我们开始吧！");
        }
        return null;
    }
}
