package cn.lyn4ever.aop.aspectj;

import cn.lyn4ever.aop.aopconfig.Teacher;
import org.springframework.stereotype.Component;

/**
 * 声明这是一个SpringBean,由Spring来管理它
 */
@Component
public class HighStudent {

    public void talk() {
        System.out.println("I am a boy");
    }

    public void walk() {
        System.out.println("I am walking");
    }

    /**
     * 这个方法添加一个teacher来做为参数,为了配置后边切入点中的args()
     * @param teacher
     */
    public void sleep(Teacher teacher) {
        System.out.println("I want to sleep");
    }
}
