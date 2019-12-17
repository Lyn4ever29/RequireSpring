package cn.lyn4ever.controlflow;

import org.springframework.aop.Advisor;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.ControlFlowPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcher;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

/**
 * 使用控制流切入点
 * <p>
 * lyn4ever
 */
public class ControlFlowPointerDemo {
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

}


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
        cat.play();//这个cat不能被再次赋值，因为只有传进来的是代理类，才会被通知
        System.out.println("I am a blackCat , I am sleeping");
    }

}

/**
 * 创建前置通知类
 */
class BeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] objects, @Nullable Object o) throws Throwable {
        System.out.println("这个方法被通知了" + method);
    }
}