# java-proxy
> java代理模式

[![Build Status](https://travis-ci.org/spring/spring.svg?branch=develop)](https://travis-ci.org/spring/spring)

## 三种代理模式

### 代理模式
定义：提供了对目标对象另外的访问方式,在不改变源码的情况下,实现对目标对象功能的扩展

使用代理模式的好处：可以在目标对象实现的基础上,增强额外的功能操作,扩展目标对象的功能  

关键：代理对象与目标对象。代理对象是对目标对象的扩展,代理对象会调用目标对象

### 静态代理
#### 实现
代理对象与目标对象要实现相同的接口 或者继承相同的父类。  
代理对象要通过调用相同的方法,来调用目标对象的方法。


#### 总结
好处：可以做到不修改目标对象功能的情况下,对目标功能扩展。

不足：会有很多代理类,接口增加方法时,目标对象与代理对象都要维护。


### 动态代理(基于jdk)
动态代理的特点：代理对象不需要实现接口
#### 实现
jdk动态代理API：Proxy的静态方法newProxyInstance()
```java
Interview proxy2 = (Interview) Proxy.newProxyInstance(JavaProxyApplication.class.getClassLoader(),
                new Class[]{Interview.class}, proxyHandler);
```

#### 总结
代理对象不需要实现接口,但目标对象一定要实现接口,否则不能使用jdk动态代理

### 动态代理(基于cglib)
#### 实现
需要引入cglib依赖  
代理对象实现MethodInterceptor接口,在intercept方法中写逻辑
```java
LiSiInterview proxy3 = (LiSiInterview) Enhancer.create(LiSiInterview.class, interceptor);
```
#### 总结
目标对象不需要实现接口

### 三种代理模式选型

在spring AOP编程中

如果加入容器的目标对象有实现接口,使用jdk动态代理  
如果加入容器的目标对象没有实现接口,使用cglib动态代理

##  使用示例
> 由于java项目一般使用spring boot,我们在maven项目中演示。
### 组件版本
|  组件   | 版本  |
|  ----  | ----  |
| spring boot | 2.3.2.RELEASE |
| cglib  | 3.3.0 |
### 整体目录结构
![avatar](./README/java-proxy-01.png)

###  执行后效果
![avatar](./README/java-proxy-02.png)

###  代码地址
https://github.com/mqcode/java-proxy.git
## 构建步骤
### 新建maven项目,仅仅引入以下依赖

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-logging</artifactId>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>

    <dependency>
        <groupId>cglib</groupId>
        <artifactId>cglib</artifactId>
        <version>3.3.0</version>
    </dependency>
</dependencies>
```



### 公共类
> 注：张三是实现了speak接口,李四没有实现speak接口
```java
/**
 * 接口
 */
public interface Interview {

    /**
     * 面试接口
     */
    void interview();
}
```

```java
/**
 * 被代理类,张三
 */
@Slf4j
public class ZhangSanInterview implements Interview {
    /**
     * 张三面试,未实现接口
     */
    @Override
    public void interview() {
        log.info("我是张三,我来面试！");
    }
}
```

```java
/**
 * 被代理类,李四
 */
@Slf4j
public class LiSiInterview {
    /**
     * 李四面试,未实现接口
     */
    public void interview(){
        log.info("我是李四,我来面试！-我没有实现接口哦！");
    }
}
```

### 基于JDK的静态代理
```java
/**
 * 代理类
 */
@Slf4j
public class HeadHunterProxy implements Interview {

    private Interview interview;

    public HeadHunterProxy(Interview speak) {
        this.interview = speak;
    }

    @Override
    public void interview() {
        log.info("你是谁？来干什么？");
        interview.interview();
        log.info("那我们开始吧！");
    }
}
```
### 基于JDK的动态代理
```java
/**
 * 代理类
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

```

### 基于cglib的动态代理

```java
/**
 * 代理类
 */
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
```

### 测试

```java
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
        Interview proxy2 = (Interview) Proxy.newProxyInstance(JavaProxyApplication.class.getClassLoader(),
                new Class[]{Interview.class}, proxyHandler);
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
```

## 关于作者
```
职业:软件工程师
微信:wlbinfo
开始制作时间:2021-12-12 15:00
```