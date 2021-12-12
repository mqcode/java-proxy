package com.example.github;

import com.example.github.cglib.LawyerInterceptor;
import com.example.github.common.LiSiSpeak;
import com.example.github.common.Speak;
import com.example.github.common.ZhangSanSpeak;
import com.example.github.jdk.LawyerProxy;
import com.example.github.jdkstatic.ZhangSanLawyerSpeak;
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
        Speak speak = new ZhangSanLawyerSpeak();
        speak.speak();
        log.info("静态代理---end");
        /**
         * jdk动态代理
         * 特点：本体必须实现接口
         * 缺点：如果某个类没有实现接口,它将无法实现动态代理
         */
        log.info("====================================");
        log.info("jdk动态代理---start");
        LawyerProxy lawyerProxy = new LawyerProxy(new ZhangSanSpeak());
        Speak speak1 = (Speak) Proxy.newProxyInstance(JavaProxyApplication.class.getClassLoader(), new Class[]{Speak.class}, lawyerProxy);
        speak1.speak();
        log.info("jdk动态代理---end");
        /**
         * cglib动态代理
         * 特点：是JDK动态代理的补充,本体不需要实现接口
         */
        log.info("====================================");
        log.info("cglib动态代理---start");
        LawyerInterceptor interceptor = new LawyerInterceptor(new LiSiSpeak());
        LiSiSpeak liSi = (LiSiSpeak) Enhancer.create(LiSiSpeak.class, interceptor);
        liSi.speak();
        log.info("cglib动态代理---end");

    }
}