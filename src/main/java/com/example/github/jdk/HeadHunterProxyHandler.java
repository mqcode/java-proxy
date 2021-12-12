package com.example.github.jdk;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * jdk动态代理
 */
@Slf4j
public class HeadHunterProxyHandler implements InvocationHandler {
    private Object object;

    public HeadHunterProxyHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("interview")) {
            log.info("你是谁？来干什么？");
            //反射
            method.invoke(object);
            log.info("那我们开始吧！");
        }
        return null;
    }
}
