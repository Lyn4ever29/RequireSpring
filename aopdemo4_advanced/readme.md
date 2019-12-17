[上一篇 SpringAOP之使用切入点创建通知](https://www.cnblogs.com/Lyn4ever/p/Lyn4ever.html)

###	SpringAOP中切点的高级使用
####	一、使用控制流切入点(ControlFlowPointcut)
>	什么是控制流切入点呢？看下面的代码(为了方便，就写进了一个公共类)
```java
class Cat {
    public void talk() {
        System.out.println("I am a cat");
    }

    public void play() {
        System.out.println("I am palying");
    }
}

class BlackCat {
    public void sleep(Cat cat) {
        cat.play();
        System.out.println("I am a blackCat , I am sleeping");
    }

}

/**
 * 创建前置通知类
 */
class BeforeAdvice implements MethodBeforeAdvice{

    @Override
    public void before(Method method, Object[] objects, @Nullable Object o) throws Throwable {
        System.out.println("这个方法被通知了"+method);
    }
}
```
*	需求：我们要给Cat的play()方法进行通知，但是呢，并不是说在调用play()方法的任何时候都通知， 只要在blackCat的sleep()方法中调用play()方法时才通知，也就是说：
```java
public static void main(String[] args) {
        Cat cat = new Cat();
        cat.play();//这个调用不会被通知
        
        BlackCat blackCat = new BlackCat();
        blackCat.sleep(cat);//这个方法中调用的paly方法才会被通知
    }
```
创建一个ControlFlowPointcut的切入点
```java
    public static void main(String[] args) {
//        Cat cat = new Cat();
//        cat.play();//这个调用不会被通知
//
//        BlackCat blackCat = new BlackCat();
//        blackCat.sleep(cat);//这个方法中调用的paly方法才会被通知


        Cat target = new Cat();

        //第一个参数是当前就是的执行要被通知的方法的类，第二个就是的执行要被通知的方法的方法名
        Pointcut pc = new ControlFlowPointcut(BlackCat.class, "sleep");
        Advisor advisor = new DefaultPointcutAdvisor(pc, new BeforeAdvice());

        ProxyFactory proxy = new ProxyFactory();
        proxy.setTarget(target);
        proxy.addAdvisor(advisor);

        Cat proxyCat = (Cat) proxy.getProxy();
        proxyCat.play();//这个方法不会被通知

        System.out.println("----------------");
        
        BlackCat blackCat = new BlackCat();
        blackCat.sleep(proxyCat);//这个方法中调用的paly方法才会被通知
    }
```

![title](https://raw.githubusercontent.com/Lyn4ever29/img/master/gitnote/2019/12/10/1575986252671-1575986252742.png)

####	二、使用组合切入点(ComposablePointcut)
>	所谓组合切入点就是利用逻辑关系(or 和 and)来对切入点进行组合，比如[上一文中说过的那几种切入点](https://www.cnblogs.com/Lyn4ever/p/Lyn4ever.html),使用逻辑关系写在一起就可以了。但是并不是直接和切入点来组合，而是组合切入点中的```ClassFilter和MethodMatcher```([为什么是这样？在这篇文章看一下Pointcut类的源码，就明白了](https://www.cnblogs.com/Lyn4ever/p/Lyn4ever.html))

*	用法：
ComposablePointcut的union()表示“或”
ComposablePointcut的intersection()表示“和”

 *	先定义三个MethodMatcher类
```java

/**
 * 匹配sleep方法名
 */
class SleepMethodMatcher extends StaticMethodMatcher{

    @Override
    public boolean matches(Method method, Class<?> aClass) {
        return method.getName().equals("sleep");
    }
}

/**
 * 匹配s开头
 */
class SStartMethodMatcher extends StaticMethodMatcher{

    @Override
    public boolean matches(Method method, Class<?> aClass) {
        return method.getName().startsWith("s");
    }
}

/**
 * 匹配k结尾
 */
class KEndMethodMatcher extends StaticMethodMatcher{

    @Override
    public boolean matches(Method method, Class<?> aClass) {
        return method.getName().endsWith("k");
    }
}
```
*	创建切入点
```java
/**
 * 创建前置通知类
 */
class BeforeAdviceDemo implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] objects, @Nullable Object o) throws Throwable {
        System.out.println("这个方法被通知了" + method);
    }
}
```

*	测试类
```java
public static void main(String[] args) {
        Cat target = new Cat();

        //这个构造方法要传入的是一个classFilter和methodMatcher实例
        ComposablePointcut pc = new ComposablePointcut(ClassFilter.TRUE, new KEndMethodMatcher());

//        pc.union(new SStartMethodMatcher());//匹配s开头的方法，和上边的切点是或的关系
//        pc.intersection(new SleepMethodMatcher()); //匹配sleep方法，和上边切点是和的关系

        Advisor advisor = new DefaultPointcutAdvisor(pc,new BeforeAdviceDemo());
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(target);
        proxyFactory.addAdvisor(advisor);

        Cat cat = (Cat) proxyFactory.getProxy();
        cat.talk();
    }

```

上边的这个ClassFilter.TRUE 和下边代码其实是一样的，意思就是返回的classFilter为true,也就是匹配所有类
```java
new ClassFilter() {
            @Override
            public boolean matches(Class<?> aClass) {
                return true;
            }
        }
```
[代码地址](https://github.com/Lyn4ever29/RequireSpring)