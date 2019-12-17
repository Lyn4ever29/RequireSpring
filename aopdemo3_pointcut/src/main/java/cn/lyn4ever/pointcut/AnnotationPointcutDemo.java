package cn.lyn4ever.pointcut;

import cn.lyn4ever.advisor.CatAdvisor;
import cn.lyn4ever.annotaion.MyAdvice;
import cn.lyn4ever.common.Cat;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

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
