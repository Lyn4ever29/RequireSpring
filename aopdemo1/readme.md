
例1.已知有这么一段代码，会打印出Hello
```java
 public static void main(String[] args) {
        sayHello();
    }
    
    public static void sayHello(){
        System.out.print("Hello");
    }
```
现在我们想打印出``` Java Hello world ```这个字符串，也就是在之前的Hello前后输出一点儿，所以写了如下代码:
```
 public static void main(String[] args) {
        System.out.print("Java ");
        sayHello();
        System.out.print(" World");
    }

    public static void sayHello(){
        System.out.print("Hello");
    }
```
这样，我们的问题就已经解决了，但是突然有一天，你们老板说，我不要打印Java，我想打印Python。那你就好办了啊，改下代码就好了么，可是突然，老板又想改成C,C++......这样一直改就不合适了吧。这时候你就要学习下AOP(面向切面编程)。
###	一、基础知识
1.在关aop的概念就不说了，直接从代码入手，慢慢过程中会说到。
2.AOP分为两种不同类型：静态AOP和动态AOP
>	静态AOP的意思就是：在编译期进行织入，就是说对切面进行的任何修改，都要进行重新编译程序。AspectJ就是一个典型的例子
*	这个织入就是一个基本概念，通白来说就是把代码插入到执行的地方。上边这个例子中就是在Hello前后多打印两句，这个过程的思想就是织入。
>	动态AOP：在代码执行过程中进行织入，它的切面代码不是编译进class文件中的。Spring AOP是一种动态AOP
###	二、使用AOP技术解决上边这个例子
基础代码不变(只不过少了静态而已，这个不影响，在代码中有解释)
```java
public void sayHello(){
        System.out.print("Hello");
    }
```
然后创建一个实现AOP Alliance(Spring AOP接口)中类的接口的切面类
```java

/**
 * 实现环绕通知的接口
 * 环绕通知就是在目标代码前后都会执行的
 */
public class MyInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        //之前
        System.out.print("Java ");

        //这一句就是执行目标代码
        Object proceed = invocation.proceed();

        //之后
        System.out.print(" World");

        return proceed;
    }
}
```
最后在main中执行织入操作
```java
public static void main(String[] args) {

        //先创建执行目标类方法的对象
        AOPDemo1 target = new AOPDemo1();

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addAdvice(new MyInterceptor());//这个MyInterceptor就是实现SpringAOP接口的类
        proxyFactory.setTarget(target);

        AOPDemo1 proxy = (AOPDemo1) proxyFactory.getProxy();
        proxy.sayHello();
    }

    //因为没有用到类名调用，也不是在main中直接调用，所以不用加静态
    public void sayHello() {
        System.out.print("Hello");
    }
```
这样，效果就和之前一样了。看不懂没关系，慢慢来。

