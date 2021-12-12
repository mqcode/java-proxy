package com.example.github.jdk;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * jdk动态代理
 */
@Slf4j
public class LawyerProxy implements InvocationHandler {
    private Object object;

    public LawyerProxy(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("speak")) {
            log.info("引用法律条文！");
            //反射
            method.invoke(object);
            log.info("打人是不对的！");
        }
        return null;
    }
}
