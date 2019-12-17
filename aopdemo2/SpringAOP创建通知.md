##	写在最前端
*	1.SpringAOP中共有六种通知类型,只要我们自定义一个类实现对应的接口，它们全都是```org.springframework.aop```包中的。
*	2.AOP的连接点可以是方法调用、方法调用本身、类初始化、对象实例化时，但是SpringAOP中全是方法调用，更简单，也最实用

|通知名称|接口|
|-|-|
|前置通知|org.springframework.aop.MethodBeforeAdvice|
|后置返回通知|org.springframework.aop.AfterReturningAdvice|
|后置通知|org.springframework.aop.AfterAdvice|
|环绕通知|org.springframework.aop.MethodInterceptor|
|异常通知|org.springframework.aop.ThrowsAdvice|
|引入通知|org.springframework.aop.IntroductionInterceptor|
---	

写一个公共类，用于目标对象
```java
public class Person {
    private String name;
    public boolean saySomething(String something){
        System.out.println("Pereson类中说了一句："+something);
return true;//默认返回true
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
```
###	一、创建前置通知(也就是目标方法调用前执行)
*	前置通知可以修改传递给方法的参数，并且可以通过抛出异常来阻止方法的执行，可以用前置通知实现用户登录的验证，SpringSecurity就是这么做的
####	1.例子：在一个方法执行前将包含方法名称的消息写入到控制台中,并且将传入的参数修改下。（文章中写的内容比较小，大多数在代码中有注释，大家可以下载代码查看）
```java

/**
 * 前置通知类
 */
public class BeforeAdvice implements MethodBeforeAdvice {


    @Override
    public void before(Method method, Object[] objects, @Nullable Object o) throws Throwable {
          //第一个参数是目标方法对象，第二个是参数,第三个是做为调用目标的object(这是personr实例)
        //打印方法名
        System.out.println("要执行的方法是："+method.getName());
        //修改参数为lyn4ever
        objects[0]="lyn4ever";//我们修改成为了lyn4ever,所以打印出来的就是lyn4ever，而不是zhangsan
    }


    public static void main(String[] args) {
        Person person = new Person();

        ProxyFactory pf  =new ProxyFactory();
        pf.addAdvice(new BeforeAdvice());
        pf.setTarget(person);

        Person proxy = (Person) pf.getProxy();

        //我这里传的参数是zhangsan,理论上它应该打印出来zhangsan
        proxy.saySomething("zhangsan");

    }

}
```
![title](https://raw.githubusercontent.com/Lyn4ever29/img/master/gitnote/2019/11/26/1574777818545-1574777818551.png)
没毛病，本来我输入的是zhangsan，在aop中将参数改为了lyn4ever,这样就完美的替换了。


###	二、后置返回通知
是在连接点（方法调用）返回后执行，这显然不能像上边那样修改参数，也不能修改返回值。但是可以抛出可以发送到堆栈的异常，同样也可以调用其他方法。
```java

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
```
![title](https://raw.githubusercontent.com/Lyn4ever29/img/master/gitnote/2019/11/26/1574779487092-1574779487096.png)
###	三、环绕通知
这人最好理解了，就是在方法调用前后都可以执行代码。看起来像是前置后后置的集合，但是它可以修改方法的返回值，因为它实现的invoke方法的返回值是Object,所以我们就可以修改，而前置通知的返回是void，所以没法修改的。甚至以至于我们可以不调用目标对象中的连接点方法，我们完全修改这个方法的全部代码。
```java
public class MyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return null;
    }
}
```
虽然这个invoke()方法中并没有提供像之前的那些参数，但是这一个invocation实例可以得到
![title](https://raw.githubusercontent.com/Lyn4ever29/img/master/gitnote/2019/11/26/1574780876140-1574780876144.png)
![title](https://raw.githubusercontent.com/Lyn4ever29/img/master/gitnote/2019/11/26/1574781099958-1574781099961.png)
代码示例
```java

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

```
可以看到，我们修改了目标方法返回的值。

