之前已经说过了SpringAOP中的几种通知类型以及如何创建简单的通知[见地址](https://www.cnblogs.com/Lyn4ever/p/11939250.html)

### 一、什么是切入点
通过之前的例子中，我们可以创建ProxyFactory的方式来创建通知，然后获取目标类中的方法。通过不同类型的通知，能对这些方法做不同的事。但是，这种方式会对整个类中的所有方法都有作用，但是很多时间我们只想对这个类中的部分方法进行通知处理，那就要使用切入点来精确地控制到特定的方法

*	也就是说，我们的切入点就是用来确定一个类中的方法（精确到方法），类似于定义一些规则一样，来找到和这个规则相匹配的类，知道这一点，往下看就容易多了。


###	二、切入点的分类
在Spring中的要创建切入点时，就要实现Pointcut类。
```java
package org.springframework.aop;

public interface Pointcut{
	ClassFilter getClassFilter();
	MethodMatcher getMethodMacher();
}
```
以上两个方法返回的类的源码如下：
*	ClassFilter 
```java
package org.springframework.aop;
/**
*	这是一个函数式接口，就是传入一个类，
*	如果这个类满足我们的要求，就返回true
*	也就是说这个切入点适用于这个类（也就是这个类不匹配我们的规则）
*/
@FunctionalInterface
public interface ClassFilter {
    boolean matches(Class<?> var1);
}
```
*	MethodMatcher 
```java
package org.springframework.aop;

import java.lang.reflect.Method;
/**
*	这个当然就是用来匹配方法了，
*	有两种类型，动态和静态，这个由isRuntime()的返回值来决定，true就是动态，false就是静态。这个类型就是决定了这个切入点是动态的还是静态的
*	
*/
public interface MethodMatcher {
    MethodMatcher TRUE = TrueMethodMatcher.INSTANCE;
	//用于静态匹配，就是和方法的参数无关
    boolean matches(Method var1, Class<?> var2);

    boolean isRuntime();
	//用于动态匹配，也就是和方法的参数有关，因为参数是会变的
    boolean matches(Method var1, Class<?> var2, Object... var3);
}
```

综上，切入点分为两种，动态切入点和静态切入点，动态切入点要每次检查方法的实参是不是满足要求，这会产生额外的开支。如果可以，如果可以，尽可能使用静态切入点。

###	三、切入点的八个实现类
|实现类|描述|
|-|-|
|org.springframework.aop.support.annotation.AnnotationMatchingPointcut|在类或方法上找特定的注解，需要JDK5以上版本|
|org.springframework.aop.aspectj.AspectJExpressionPointcut|使用AspectJ织入器以AspectJ语法评估切入点表态式 |
|org.springframework.aop.support.ComposablePointcut|使用诸如union()和intersection()等操作组合两个或多个切入点|
|org.springframework.aop.support.ControlFlowPointcut|是一种特殊的切入点，它们匹配另一个方法的控制流中的所有方法，即任何作为另一个方法的结果而直接或间接调用的方法|
|org.springframework.aop.support.JdkRegexpMethodPointcut|对方法名使用正则表达式定义切入点，要JDK4以上|
|org.springframework.aop.support.NameMatchMethodPointcut|顾名思义，这是对方法名称列表进行简单的匹配|
|org.springframework.aop.support.DynamicMethodMatcherPointcut|这个类作为创建动态切入点的基类|
|org.springframework.aop.support.StaticMethodMatcherPointcut|作为创建表态切入点的基类|

###	四、使用StaticMethodMatcherPointcut来创建静态切入点
-	创建一个类，两个方法。我们的目的就是只在walk()方法中创建环绕通知，打印一句，"I am a cute cat."
```java
public class Cat {
    public void sleep(){
        System.out.println("sleep....");
    }
    public void walk(){
        System.out.println("walking....");
    }
}
```
-	创建切入点
```java
public class MethodPointcutDemo extends StaticMethodMatcherPointcut {

    @Override
    public boolean matches(Method method, Class<?> aClass) {
        return method.getName().equals("walk");
    }

    @Override
    public ClassFilter getClassFilter() {
        return clz -> clz == Cat.class;
        
//        上边的lambda表达式等于下边的这个
//        return new ClassFilter() {
//            @Override
//            public boolean matches(Class<?> clz) {
//                return clz == Cat.class;
//            }
//        };
    }
}
```
>	上边的lambda表达式可查看[https://www.cnblogs.com/Lyn4ever/p/11967959.html](https://www.cnblogs.com/Lyn4ever/p/11967959.html),后边出现时只写lambda表达式，且不再说明

>	在上边这个方法中，当然，我们可以不用实现getClassFilter()方法，因为这个方法已经被上级实现过了，我们就可以在matches方法中直接去判断这个类是不是Cat.class
-	通知类（这个和上一次是一样的，创建一个环绕通知）
```java
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class CatAdvisor implements MethodInterceptor{

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        //最不靠谱的方法，我们可以在这里判断这个method的是不是walk,从而要不要进行通知
        System.out.println("I am a cute Cat.");
        Object proceed = invocation.proceed();
        return proceed;
    }
}
```
>	当然，我们在这里也是可以判断这个方法名和类名的，为什么还要用切入点呢。可是这并不靠谱，我们中需要在这里实现我们的逻辑代码，而通过切入点来控制哪个类，哪个方法要被通知，这样更灵活。
-	测试方法
```java
public static void main(String[] args) {
        Cat cat = new Cat();

        Pointcut pointcut = new MethodPointcutDemo();//切入点实例
        Advice advice = new CatAdvisor();//通知类实例（就是我们的通知代码存放的类的对象）

        Advisor advisor = new DefaultPointcutAdvisor(pointcut, advice);//切面类，就是切入点和通知类的集合

        ProxyFactory proxyFactory = new ProxyFactory();
        //和之前代码的区别就是这一句，这里我们使用的是切入点控制，如果把这句注释了，就要用设置通知类
        proxyFactory.addAdvisor(advisor);//设置切面类，包含切入点（控制通知点）和通知类（逻辑代码）
        
        //如果注释了上面一句，用这一句的话，就会对这个类中的所有方法都通知
//        proxyFactory.addAdvice(advice);//设置通知类
        proxyFactory.setTarget(cat);
        Cat proxy = (Cat) proxyFactory.getProxy();

        proxy.sleep();
        proxy.walk();
    }
```
运行结果肯定就是我们想的那样
```
sleep....
I am a cute Cat.
walking....
```
###	五、使用DyanmicMatcherPointcut创建动态切入点
这个和上边的静态切入点是一样的，只不过是让传入方法的参数满足一定要求时，才会执行通知。由于篇幅原因，就不写了，在本文最后下载代码就能看懂。
###	六、其他类型的PointCut的实现类
####	1.简单的名称匹配 ( *NameMatchMethodPointcut* )
目标类和通知类还是之前的Cat类，之前的切入点的实现类不用写，因为这个类已经做了默认实现,感兴趣的可以看下它的源码，很简单的，就是匹配类名，和我们刚才创建的静态切入点差不多。
```java
public class NameMatchPointcutDemo {
    public static void main(String[] args) {
        Cat cat = new Cat();

        //这个类已经是个实现类，我们就不需要再去写实现类了
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.addMethodName("walk");

        Advisor advisor = new DefaultPointcutAdvisor(pointcut,new CatAdvisor());

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addAdvisor(advisor);
        proxyFactory.setTarget(cat);
        Cat proxy = (Cat) proxyFactory.getProxy();

        proxy.sleep();
        proxy.walk();
    }
}
```
#### 2.使用正则表达式创建切入点 ( *JdkRegexpMethodPointcut* )
只写测试类，其他的都和上边一样
```java
public class JdkRegexpPointcutDemo {

    public static void main(String[] args) {
        Cat cat = new Cat();

        JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
        pointcut.setPattern(".*ee.*");//匹配中间有ee字母的，sleep()

        Advisor advisor = new DefaultPointcutAdvisor(pointcut,new CatAdvisor());

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addAdvisor(advisor);
        proxyFactory.setTarget(cat);
        Cat proxy = (Cat) proxyFactory.getProxy();

        proxy.sleep();
        proxy.walk();
    }
}
```
#### 3.使用AspectJ切入点表达式创建切入点 ( *AspectJExpressionPointcut* )
使用AspectJ时要加入相关依赖
```xml
    <dependency>
      <groupId>org.aspectj</groupId>
      <artifactId>aspectjrt</artifactId>
      <version>1.9.1</version>
    </dependency>
    <dependency>
      <groupId>org.aspectj</groupId>
      <artifactId>aspectjweaver</artifactId>
      <version>1.9.1</version>
    </dependency>
```
```java
public class AspectJExpressionPointcutDemo {
    public static void main(String[] args) {
        Cat cat = new Cat();

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* walk*(..))");

        Advisor advisor = new DefaultPointcutAdvisor(pointcut, new CatAdvisor());

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addAdvisor(advisor);
        proxyFactory.setTarget(cat);
        Cat proxy = (Cat) proxyFactory.getProxy();

        proxy.sleep();
        proxy.walk();
    }
}
```
这个execution表达式的意思是：任何以walk开头的，具有任何参数和任何返回值的方法

#### 4.创建注解匹配的切入点 ( *AnnotationMatchingPointcut* )
首先自定义一个注解，如果不是很懂，参考[Java中自定义注解类，并加以运用](https://www.cnblogs.com/Lyn4ever/p/11594533.html)
```java
/**
 * 这个注解是用于运行时期、可用于类、方法上
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface MyAdvice {
}
```
然后在目标方法上添加这个注解（要被通知的类）
```java
    /**
     * 为了不与之前的冲突，就新写了一个方法
     */
    @MyAdvice
    public void eat(){
        System.out.println("eating....");
    }
```
然后在main方法中指定这个注解名：
```java
public class AnnotationPointcutDemo {
    public static void main(String[] args) {
        Cat cat = new Cat();

        AnnotationMatchingPointcut pointcut = AnnotationMatchingPointcut
                .forMethodAnnotation(MyAdvice.class);
        //这个类还有一个.forClassAnnotation()方法，就是指定类的

        Advisor advisor = new DefaultPointcutAdvisor(pointcut, new CatAdvisor());

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addAdvisor(advisor);
        proxyFactory.setTarget(cat);
        Cat proxy = (Cat) proxyFactory.getProxy();

        proxy.sleep();//不会被通知
        proxy.walk();//不会被通知
        proxy.eat();//会被通知
    }
}
```
[代码已上传至github,如果喜欢，就给个star](https://github.com/Lyn4ever29/RequireSpring.git)

上一篇：[SpringAOP基础](https://www.cnblogs.com/Lyn4ever/p/11938449.html)
