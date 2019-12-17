package cn.lyn4ever.aop1;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

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
