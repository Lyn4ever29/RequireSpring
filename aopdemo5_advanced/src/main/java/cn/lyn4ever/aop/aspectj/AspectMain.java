package cn.lyn4ever.aop.aspectj;

import cn.lyn4ever.aop.aopconfig.Teacher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class AspectMain {
    public static void main(String[] args) {
//        xmlConfig();
        javaConfig();

    }

    private static void javaConfig() {
        GenericApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);
        HighStudent student = (HighStudent) context.getBean("highStudent");
        student.sleep(new Teacher());//应该被环绕通知
        System.out.println();

        student.talk();//前置通知
        System.out.println();

        student.walk();//不会被通知
        System.out.println();
    }

    private static void xmlConfig(){
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.load("application_aspect.xml");
        context.refresh();

        HighStudent student = (HighStudent) context.getBean("highStudent");
        student.sleep(new Teacher());//应该被环绕通知
        System.out.println();

        student.talk();//前置通知
        System.out.println();

        student.walk();//不会被通知
        System.out.println();
    }
}
