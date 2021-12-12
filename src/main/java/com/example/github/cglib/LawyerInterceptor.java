package com.example.github.cglib;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
@Slf4j
public class LawyerInterceptor implements MethodInterceptor {
    private Object object;

    public LawyerInterceptor(Object object) {
        this.object = object;
    }
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if (method.getName().equals("speak")) {
            log.info("引用法律条文！");
            //反射
            method.invoke(object);
            log.info("打人是不对的！");
        }
        return null;
    }
}
