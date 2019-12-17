###	关于Spring的的简介就不多说了，百度一大堆，我们直接从如何使用说起

###	1.使用Maven导入依赖
```xml
<dependencies>
        <!-- Spring依赖 -->
        <!-- 1.Spring核心依赖 -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>4.3.7.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-beans</artifactId>
            <version>4.3.7.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>4.3.7.RELEASE</version>
        </dependency>
        <!-- 2.Spring dao依赖 -->
        <!-- spring-jdbc包括了一些如jdbcTemplate的工具类 -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>4.3.7.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-tx</artifactId>
            <version>4.3.7.RELEASE</version>
        </dependency>
        <!-- 3.Spring web依赖 -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-web</artifactId>
            <version>4.3.7.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>4.3.7.RELEASE</version>
        </dependency>
        <!-- 4.Spring test依赖：方便做单元测试和集成测试 -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>4.3.7.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.122</version>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>5.4.0</version>
        </dependency>

    </dependencies>
```
###	2.创建我们的实体类
```java
package cn.lyn4ever.firstday.bean;

public class Computer {

    private String size;
    private String color;


    public Computer() {
    }

    public Computer(String size, String color) {
        this.size = size;
        this.color = color;
    }


    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}

```
###	3.创建一个application.xml配置文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--这个类就是我们的实体类-->
    <bean name="computer" class="cn.lyn4ever.firstday.bean.Computer">
    </bean>
</beans>

```
###	4.在测试类中就可以取到这个类了
```java
package cn.lyn4ever.firstday.test;

import cn.lyn4ever.firstday.bean.Computer;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 为了方便，我就直接将测试类写在了这里
 */
public class FirstTest {

    @Test
    public void fun() {
        //参数就是对应的Spring的配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");

        //得到了一个Computer类
        Computer computer = context.getBean("computer", Computer.class);
        //上边这句和下边这名效果是一样的
        // Computer computer = new Computer();

        //对这个类进行操作
        computer.setColor("red");

        System.out.println(computer.getColor());
    }
}
```
[以上代码地址](https://github.com/Lyn4ever29/RequireSpring/tree/master/firstdemo)
