package cn.lyn4ever.composable;

import org.springframework.aop.*;
import org.springframework.aop.BeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcher;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

/**
 * 组合切入点
 */
public class ComposablePointcutDemo {

    public static void main(String[] args) {
        Cat target = new Cat();

        //这个构造方法要传入的是一个classFilter和methodMatcher实例
        ComposablePointcut pc = new ComposablePointcut(new ClassFilter() {
            @Override
            public boolean matches(Class<?> aClass) {
                return true;
            }
        }, new KEndMethodMatcher());

//        pc.union(new SStartMethodMatcher());//匹配s开头的方法，和上边的切点是或的关系
//        pc.intersection(new SleepMethodMatcher()); //匹配sleep方法，和上边切点是和的关系
        Advisor advisor = new DefaultPointcutAdvisor(pc,new BeforeAdviceDemo());
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(target);
        proxyFactory.addAdvisor(advisor);

        Cat cat = (Cat) proxyFactory.getProxy();
        cat.talk();
    }

//
}


class Cat {
    public void talk() {
        System.out.println("I am a cat");
    }

    public void play() {
        System.out.println("I am palying");
    }
}


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

/**
 * 创建前置通知类
 */
class BeforeAdviceDemo implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] objects, @Nullable Object o) throws Throwable {
        System.out.println("这个方法被通知了" + method);
    }
}