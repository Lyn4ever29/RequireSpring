package cn.lyn4ever.aop.aopconfig;

import org.springframework.context.support.GenericXmlApplicationContext;

public class MainDemo {
    public static void main(String[] args) {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.load("application1.xml");
        context.refresh();

        Teacher teacher = (Teacher) context.getBean("teacherOne");
        teacher.tellStudent();

//        使用application2.xml
        GenericXmlApplicationContext context2 = new GenericXmlApplicationContext();
        context2.load("application2.xml");
        context2.refresh();

        Teacher teacherTwo = (Teacher) context2.getBean("teacherTwo");
        teacherTwo.tellStudent();

    }
}
