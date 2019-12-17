package cn.lyn4ever.pointcut;

import cn.lyn4ever.advisor.CatAdvisor;
import cn.lyn4ever.common.Cat;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;

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
