*	Ioc，即是控制反转，直白来说就是通过Spring来管理Java对象，而不是通过我们之前的new对象的方式来创建对象。
*	DI，即是依赖注入，它是IoC的核心。
##	一、控制反转的类型
Spring的控制反转类型主要有以下几种：**依赖拉取** 、**上下文依赖查找**、**构造函数依赖注入**、**setter依赖注入**
##	二、使用xml配置的方式时注入
###	1.依赖拉取：
这也就是最简单的注入类的方式，就是入门篇中的那段Java代码，如下：从xml配置文件中读取对应的bean.
>	在xml文件中定义了如下类
```xml
<!--这个类就是我们的实体类-->
    <bean name="computer" class="cn.lyn4ever.spring.bean.Computer">
    </bean>
```
>	然后在测试类中可以用如下代码获取bean
```java
 //参数就是对应的Spring的配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");

        //得到了一个Computer类
        Computer computer = context.getBean("computer", Computer.class);
```
###	2.上下文依赖查找
这个查找是依赖于管理资源的容器执行的，如：Tomcat,JBoss等，这里先不做记录
###	3.构造函数注入
在Computer类中先写两个构造函数
```java

    public Computer() {
    }

    public Computer(String size, String color) {
        this.size = size;
        this.color = color;
    }
```
然后在xml配置文件中配置上bean
```xml
  <!--构造函数注入1-->
    <bean name="computerByCon1" class="cn.lyn4ever.spring.bean.Computer">
        <constructor-arg name="color" value="red"/>
        <constructor-arg name="size" value="middle"/>
    </bean>

    <!--构造函数注入2,上边是利用属性的name来注入，这里是用角标的方式，本质上是一样的-->
    <bean name="computerByCon2" class="cn.lyn4ever.spring.bean.Computer">
        <constructor-arg index="0" value="green"/>
        <constructor-arg index="1" value="large"/>
    </bean>
```
以上配置了两种方式（通过name和index的方式）配置了两个bean，但是要注意：这个```constructor-arg```标签的数量要和我们在java类中写的构造函数对应上
```
    @Test
    public void fun2() {
        //参数就是对应的Spring的配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");

        //得到了一个Computer类
        Computer computer1 = context.getBean("computerByCon1", Computer.class);
        System.out.println(computer1.toString());//Computer{size='middle', color='red'}

        Computer computer2 = context.getBean("computerByCon2", Computer.class);
        System.out.println(computer2.toString());//Computer{size='green', color='large'}

    }
```
###	4.setter方式注入
就是通过我们在java类中配置写的setter方法（此处和getter方法没有关系，没有getter方法也可以注入java bean）
```xml
<!--setter方法注入-->
    <bean name="computerBySetter" class="cn.lyn4ever.spring.bean.Computer">
        <property name="color" value="red"/>
        <property name="size" value="small"/>
        <!--这个property标签只与类中的setter()方法有关，只有写了setter方法，才能注入-->
    </bean>
```

```java

```   @Test
    public void fun3() {
        //参数就是对应的Spring的配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");

        //得到了一个Computer类
        Computer computer = context.getBean("computerBySetter", Computer.class);
        System.out.println(computer.toString());//Computer{size='small', color='red'}
    }
```




