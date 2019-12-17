package cn.lyn4ever.spring.test;


import cn.lyn4ever.spring.bean.Computer;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 为了方便，我就直接将测试类写在了这里
 */
public class FirstTest {

    @Test
    public void fun() {
        //参数就是对应的Spring的配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");

        //得到了一个Computer类
        Computer computer = context.getBean("computer", Computer.class);
        //上边这句和下边这名效果是一样的
        // Computer computer = new Computer();

        //对这个类进行操作

        computer.setColor("red");

        System.out.println(computer.getColor());
    }

    @Test
    public void fun2() {
        //参数就是对应的Spring的配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");

        //得到了一个Computer类
        Computer computer1 = context.getBean("computerByCon1", Computer.class);
        System.out.println(computer1.toString());//Computer{size='middle', color='red'}

        Computer computer2 = context.getBean("computerByCon2", Computer.class);
        System.out.println(computer2.toString());//Computer{size='green', color='large'}

    }

    @Test
    public void fun3() {
        //参数就是对应的Spring的配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");

        //得到了一个Computer类
        Computer computer = context.getBean("computerBySetter", Computer.class);
        System.out.println(computer.toString());//Computer{size='small', color='red'}
    }

}

