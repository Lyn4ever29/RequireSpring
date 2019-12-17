package cn.lyn4ever.advisor;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class CatAdvisor implements MethodInterceptor{

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        //最不靠谱的方法，我们可以在这里判断这个method的是不是walk,从而要不要进行通知
        System.out.println("I am a cute Cat.");
        Object proceed = invocation.proceed();
        return proceed;
    }
}