package cn.lyn4ever.aop;

import cn.lyn4ever.common.Person;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;

/**
 * 环绕通知
 */
public class MyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        
        //在这个invoation中有一切我们想要的方法相关
        System.out.println("类名是:"+invocation.getThis().getClass().getName());
        System.out.println("目标方法是:"+invocation.getMethod().getName());

        Object[] arguments = invocation.getArguments();//这个就是参数
        System.out.println("第一个参数 是:"+arguments[0]);

        //我们修改第一个参数为lyn4ever
        arguments[0]="lyn4ever";


        invocation.proceed();//执行目标方法


        System.out.println("这个是在之后执行的");

        return false;//修改返回值
    }


    public static void main(String[] args) {
        Person person = new Person();

        ProxyFactory pf = new ProxyFactory();
        pf.addAdvice(new MyMethodInterceptor());//注意修改这个为当前类中的通知类
        pf.setTarget(person);

        Person proxy = (Person) pf.getProxy();
        boolean flag = proxy.saySomething("zhangsan");
        System.out.println(flag);//方法本来是要返回true的
    }
}
