package cn.lyn4ever.aop.proxyfactory;

import cn.lyn4ever.aop.aopconfig.Teacher;
import org.springframework.context.support.GenericXmlApplicationContext;

public class AopConfigDemo {
    public static void main(String[] args) {
            GenericXmlApplicationContext context = new GenericXmlApplicationContext();
            context.load("application_aop_config.xml");
            context.refresh();

            Teacher teacher = (Teacher) context.getBean("teacher");
            teacher.tellStudent();

    }
}
