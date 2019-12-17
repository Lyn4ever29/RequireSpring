package cn.lyn4ever.pointcut;

import cn.lyn4ever.advisor.CatAdvisor;
import cn.lyn4ever.common.Cat;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

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
}
