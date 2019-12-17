package cn.lyn4ever.aop;

import cn.lyn4ever.common.Person;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

/**
 * 前置通知类
 */
public class BeforeAdvice implements MethodBeforeAdvice {


    @Override
    public void before(Method method, Object[] objects, @Nullable Object o) throws Throwable {
        //第一个参数是目标方法对象，第二个是参数,第三个是做为调用目标的object(这是personr实例)

        //打印方法名
        System.out.println("要执行的方法是：" + method.getName());
        //修改参数为lyn4ever
        objects[0] = "lyn4ever";//我们修改成为了lyn4ever,所以打印出来的就是lyn4ever，而不是zhangsan

        //如果我们在这里写一个异常，程序就会停止运行，这样就可以阻止程序运行
//        int i = 10/0;
    }


    public static void main(String[] args) {
        Person person = new Person();

        ProxyFactory pf = new ProxyFactory();
        pf.addAdvice(new BeforeAdvice());
        pf.setTarget(person);

        Person proxy = (Person) pf.getProxy();

        //我这里传的参数是zhangsan,理论上它应该打印出来zhangsan
        proxy.saySomething("zhangsan");

    }

}
