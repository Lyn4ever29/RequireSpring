package cn.lyn4ever.aop;

import cn.lyn4ever.common.Person;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

/**
 * 后置返回通知
 */
public class AfterReturnAdvice implements AfterReturningAdvice {

    @Override
    public void afterReturning(@Nullable Object o, Method method, Object[] objects, @Nullable Object o1) throws Throwable {
        /*
        参数和前置通知是一样的
        这个是在返回之后调用，因此，person中的saySomething会先打印，我们在这里修改的参数不起作任何作用
         */

        System.out.println("调用的方法是:"+method.getName()+"这句是在saySomething之后");//这句是在saySomething之后
        objects[0]="lyn4ever";//这句可以修参数，但是之前的方法已经执行过了，所以不起任何作用

        System.out.println("我们修改了参数为:"+objects[0]+"但是没有任何用");//这时候这个参数并不会传到person.saysomething(),因为已经调用过了

    }


    public static void main(String[] args) {
        Person person = new Person();

        ProxyFactory pf = new ProxyFactory();
        pf.addAdvice(new AfterReturnAdvice());//注意修改这个为当前类中的通知类
        pf.setTarget(person);

        Person proxy = (Person) pf.getProxy();
        proxy.saySomething("zhangsan");
    }
}
