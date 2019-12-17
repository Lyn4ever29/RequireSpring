package cn.lyn4ever.aop.proxyfactory;

import org.aspectj.lang.JoinPoint;

/**
 * 这回这个类并不需要继承或实现通知类,
 * 我们只需要传入一个连接点JoinPoint的参数,spring会自动传递
 * 当然,如果你不想在通知中对方法进行的内容进行读取或修改,可以不用传
 * 因为在xml中有配置,
 */
public class BeforeAdvice {
    public void beforeSaySomething(JoinPoint joinPoint) {
        System.out.println("这个方法被通知了" + joinPoint.getSignature().getName());
    }
}
