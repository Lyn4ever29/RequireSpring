package cn.lyn4ever.pointcut;

import cn.lyn4ever.advisor.CatAdvisor;
import cn.lyn4ever.common.Cat;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

public class NameMatchPointcutDemo {
    public static void main(String[] args) {

        Cat cat = new Cat();

        //这个类已经是个实现类，我们就不需要再去写实现类了
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.addMethodName("walk");

        Advisor advisor = new DefaultPointcutAdvisor(pointcut,new CatAdvisor());

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addAdvisor(advisor);//设置切面类，包含切入点（控制通知点）和通知类（逻辑代码）

        proxyFactory.setTarget(cat);
        Cat proxy = (Cat) proxyFactory.getProxy();

        proxy.sleep();
        proxy.walk();


    }
}
