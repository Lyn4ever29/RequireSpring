#	Spring中使用数据库的事务

在整个JavaWeb项目开发中，事务是用来开发可靠性网络应用程序的最关键部分。当应用程序与后端资源进行交互时，就会用到事务，这里的后端资源包括数据库、MQ、ERP等。而数据库事务是最常见的类型，而我们常说的事务也就是狭义上的与关系型数据库交互的事务。

事务主要分为本地事务和全局事务。全局事务又称分布式事务，本地事务就是当应用程序连接单个数据库资源时的事务，也是本文化主要讨论的内容。

## 一、事务的一些基本概念

#### 事务的属性（ACID）：

- 原子性
- 一致性
- 隔离性
- 持久性

####	白话“事务”

事务有三个状态（或者说是过程）：**开始、提交、回滚**。

假设有这么一个场景：张三和李四各有100元，有一天，张三要给李四转10元。

相当于目前的微信转账，张三给李四发了10元的转账。有以下三种状态

![](https://gitee.com/lyn4ever/picgo-img/raw/master/img/20200405223244.png)

上边这个例子有一处不恰当的地方就是，就算李四没有操作这10元时，张三已经少了10元，这一点和事务有出入 ，我们就假装如果李四不接收或者退回这10元，张三的微信钱包里还有100元。但是在微信中有那么多的人相互转账，每一次转账就是一个事务，我们就要把这些事务进行隔离，但是它有不同的隔离级别（见下）

#### 事务的隔离级别

| 隔离级别         | 描述                                                         | 举例                                                         |
| ---------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| DEFAULT          | 底层数据库存储的默认隔离级别                                 |                                                              |
| READ_UNCOMMITTED | 最低的隔离级别，可以说它并不是事务，因为它允许其他事务来读取未来提交的数据 | 上边的例子中，就算李四没有收这10元，其他人也能读取到李四多了10元。 |
| READ_COMMITTED   | 大多数数据库的默认级别，它确保其他事务可以读取其他事务已经提交的数据 | 只有当李四对这10元进行操作(接收或者退回)时，别人才能看到这两个的余额变化。 |
| REPEATABLE_READ  | 比上一个更为严格，它确保在选择了数据后，如果其他事务对这个数据进行了更改，就可以选择新的数据。 | 上边的是在转账过程中，就算别人给张三又转了10元，在这个事务提交前，张三一直认为自己只有100元。但是这个类型中，张三在转账过程中，可以查到自己有110元 |
| SERIALIZABLE     | 可序列化，是最严格最可靠的隔离级别，让所有事务一个接着一个地运行 | 系统让每个人转账事务一个一个地执行，就不会有任何错误了（当然，这里的事务不单单指转账这一个事务） |

####	事务的传播类型

也就是当前事务开始的机制和时间，相当于这么多的人之间的微信转账应该怎么进行

| 传播类型                  | 描述                                                         |
| ------------------------- | ------------------------------------------------------------ |
| PROPAGATION_REQUIRED      | 如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中； |
| PROPAGATION_SUPPORTS      | 支持当前事务，如果当前没有事务，就以非事务方式执行；         |
| PROPAGATION_MANDATORY     | 使用当前的事务，如果当前没有事务，就抛出异常；               |
| PROPAGATION_REQUIRES_NEW  | 新建事务，如果当前存在事务，把当前事务挂起；                 |
| PROPAGATION_NOT_SUPPORTED | 以非事务方式执行操作，如果当前存在事务，就把当前事务挂起；   |
| PROPAGATION_NEVER         | 以非事务方式执行，如果当前存在事务，则抛出异常；             |
| PEOPAGATION_NESTED        | 如果当前存在事务，则在潜逃事务内执行。如果当前没有事务，则执行PROPAGATION_REQUIRED列斯的操作； |

## 二、Spring中解决事务问题

在Spring中解决事务问题有两种：**声明式事务**和**编程式事务**（不建议使用）

Spring中支持事务的最底层接口是```PlatformTransactionManager```，而我们使用的只能它的子类 

```java
public interface PlatformTransactionManager {
    //获取事务状态
    TransactionStatus getTransaction(@Nullable TransactionDefinition var1) throws TransactionException;
	//提交
    void commit(TransactionStatus var1) throws TransactionException;
	//回滚
    void rollback(TransactionStatus var1) throws TransactionException;
}	
```

这个接口中主要用了TransactionDefinition和TransactionStatus两个类。有兴趣的可以看一下。下边这是它的子类图，我们这里使用的是DataSourceTransactionManager作为事务管理类，不管使用何种方式，PlatformTransactionManager这个接口的子类一定要有。

![](https://gitee.com/lyn4ever/picgo-img/raw/master/img/20200406000705.png)

###	1. 声明式事务

* 使用注解	

  配置文件如下：

```xml
<!--引入公共的配置文件-->
<import resource="application-context.xml"/>

<!--Spring提供的事务管理器-->
<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <constructor-arg ref="dataSource"/>
</bean>

<!--
    开启事务注解
    这里有个小技巧，如果你的事务管理bean名不是transactionManager
    就要给这个标签配置transaction-manager来指定
    -->
<tx:annotation-driven/>

<!--    配置spring扫描注解注入service类-->
<context:component-scan base-package="cn.lyn4ever.service"/>
```

​		然后在类或方法上加上这个@Transactional注解就可以

```java
@Transactional
public void insertOne(){
    Store store  =new Store();
    store.setTitle("华为P30");
    storeMapper.insertOne(store);

    int j = 10/0;//指定报错，让事务回滚
}
```

* 使用AOP配置

使用aop的话，我们只需要进行配置，可以对我们写的业务代码无任何侵入。如果对AOP知识不是很了解，可以参考我之前的AOP系列教程[Spring学习笔记](https://github.com/Lyn4ever29/RequireSpring)，AOP的配置也有多种，这里就直接使用aop名称空间了

```xml
<import resource="application-context.xml"/>

<!--Spring提供的事务管理器-->
<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <constructor-arg ref="dataSource"/>
</bean>

<tx:advice id="txAdvice">
    <tx:attributes>
        <!-- 对单独方法配置属性-->
        <tx:method name="insert*" rollback-for="java.lang.Exception"/>
        <tx:method name="*"/>
    </tx:attributes>
</tx:advice>

<aop:config>
    <aop:pointcut id="serviceTrans" expression="execution(* cn.lyn4ever.serviceaop.*.*(..))"/>
    <aop:advisor advice-ref="txAdvice" pointcut-ref="serviceTrans"/>
</aop:config>

<!--    配置spring扫描注解注入service类-->
<context:component-scan base-package="cn.lyn4ever.serviceaop"/>
```

* 以上两种方式的对比：
  * 使用注解能更加细粒度地进行控制，因为并不是所有service方法都需要事务。而使用AOP使用的面向切面编程，所以可以大批量的进行控制，而一般都是在service层进入切入的。
  * 使用注解的话，配置简单，AOP的配置稍微复杂点儿。
  * 如果是新项目的话，建议从一开始就使用注解式开发。如果是更改之前没有用过事务（一般成熟的程序员不会这么干）的项目，或者无法修改源代码的情况下，建设使用AOP。
  * 个人经验，建议使用注解开发，能灵活的配置每一个方法及类。使用AOP的话，有时候调试起来不太方便，如果你的调试内容跨越了一个service方法，会进入aop通知方法，很麻烦。

### 2.  编程式事务

顾名思义，就是将事务的操作直接写在业务代码中，这样做最简单，但是最不建议。有两种方式，一种就是将PlatformTransactionManager的实例注入到bean中，使用它。另一种就是使用Spring为我们提供的TransactionTemplate。这里直接使用第二种，这时，我们只需要使用Spring注入注入transactionManager和这两个类，但是为了不和之前的配置混淆，我直接new这两个对象了，也就是说，使用编程式事务，只需要这两个对象就够了，不需要其他任何有关事务的配置，只需要一个数据源

```java
@Autowired
private DataSource dataSource;

@Test
public void fun() {
    DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
    //设置数据源，这个数据源的bean是由Spring提供的
    transactionManager.setDataSource(dataSource);
    TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
    transactionTemplate.execute(txStatus -> {
        Store store = new Store();
        store.setTitle("小米11");
        storeMapper.insertOne(store);
        //制造错误，让事务回滚
        int i = 10 / 0;
        return null;
    });
}
```



关注微信公众号“小鱼与Java”获取代码地址和更多学习资料

![](https://lyn4ever.gitee.io/img/wx/gzh2.png)