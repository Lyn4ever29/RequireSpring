package cn.lyn4ever.aop1;

import org.springframework.aop.framework.ProxyFactory;

/**
 * 使用AOP解决方案
 */
public class AOPDemo1 {
    public static void main(String[] args) {

        //先创建执行目标类方法的对象
        AOPDemo1 target = new AOPDemo1();

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addAdvice(new MyInterceptor());//这个MyInterceptor就是实现SpringAOP接口的类
        proxyFactory.setTarget(target);

        AOPDemo1 proxy = (AOPDemo1) proxyFactory.getProxy();
        proxy.sayHello();

    }

    //因为没有用到类名调用，也不是在main中直接调用，所以不用加静态
    public void sayHello() {
        System.out.print("Hello");
    }


}
