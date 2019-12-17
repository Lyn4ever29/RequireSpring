package cn.lyn4ever.pointcut;

import cn.lyn4ever.advisor.CatAdvisor;
import cn.lyn4ever.common.Cat;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

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
