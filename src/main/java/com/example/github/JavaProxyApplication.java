package com.example.github;

import com.example.github.cglib.HeadHunterProxyInterceptor;
import com.example.github.common.Interview;
import com.example.github.common.LiSiInterview;
import com.example.github.common.ZhangSanInterview;
import com.example.github.jdk.HeadHunterProxyHandler;
import com.example.github.jdkstatic.HeadHunterProxy;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

/**
 * Hello world!
 */
@Slf4j
public class JavaProxyApplication {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        /**
         * 静态代理
         */
        log.info("====================================");
        log.info("静态代理---start");
        //目标对象
        Interview interview1 = new ZhangSanInterview();
        //代理对象
        Interview proxy1 = new HeadHunterProxy(interview1);
        //执行代理对象的方法
        proxy1.interview();
        log.info("静态代理---end");
        /**
         * jdk动态代理
         * 特点：本体必须实现接口
         * 缺点：如果某个类没有实现接口,它将无法实现动态代理
         */
        log.info("====================================");
        log.info("jdk动态代理---start");
        //目标对象
        Interview interview2 = new ZhangSanInterview();
        HeadHunterProxyHandler proxyHandler = new HeadHunterProxyHandler(interview2);
        //代理对象
        Interview proxy2 = (Interview) Proxy.newProxyInstance(JavaProxyApplication.class.getClassLoader(), new Class[]{Interview.class}, proxyHandler);
        //执行代理对象的方法
        proxy2.interview();
        log.info("jdk动态代理---end");
        /**
         * cglib动态代理
         * 特点：是JDK动态代理的补充,本体不需要实现接口
         */
        log.info("====================================");
        log.info("cglib动态代理---start");
        //目标对象
        LiSiInterview interview3 = new LiSiInterview();
        HeadHunterProxyInterceptor interceptor = new HeadHunterProxyInterceptor(interview3);
        //代理对象
        LiSiInterview proxy3 = (LiSiInterview) Enhancer.create(LiSiInterview.class, interceptor);
        //执行代理对象的方法
        proxy3.interview();
        log.info("cglib动态代理---end");

    }
}