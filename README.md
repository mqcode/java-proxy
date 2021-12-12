# java-proxy
java代理机制

## 新建maven项目,pom文件设置为

```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <parent>
    <!--spring boot 基础版本-->
    <artifactId>spring-boot-starter-parent</artifactId>
    <groupId>org.springframework.boot</groupId>
    <version>2.3.2.RELEASE</version>
  </parent>

  <groupId>com.example.github</groupId>
  <artifactId>java-proxy</artifactId>
  <version>1.0.0</version>
  <packaging>jar</packaging>

  <name>java-proxy</name>
  <url>https://mqcode.github.io</url>
  <description>
    java代理机制
  </description>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
  </properties>

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

  <build>
    <resources>
      <resource>
        <directory>src/main/resources</directory>
        <filtering>true</filtering>
      </resource>
    </resources>
  </build>
</project>
```
## 公共类
```java
public interface Speak {
    void speak();
}
```
## 整体目录结构



```java
@Slf4j
public class ZhangSanSpeak implements Speak{
    @Override
    public void speak() {
        log.info("我老婆打我！");
    }
}
```

```java
@Slf4j
public class LiSiSpeak {
    public void speak(){
        log.info("我老婆打我！-我没有实现接口哦！");
    }
}
```

## 基于JDK的静态代理
```java
@Slf4j
public class ZhangSanLawyerSpeak implements Speak {

    private ZhangSanSpeak zhangSanSpeak = new ZhangSanSpeak();

    @Override
    public void speak() {
        log.info("引用法律条文！");
        zhangSanSpeak.speak();
        log.info("打人是不对的！");
    }
}
```
## 基于JDK的动态代理
```java
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
```

## 基于cglib的动态代理

```java
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
```