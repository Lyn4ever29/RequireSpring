package cn.lyn4ever.aop.aspectj;

import cn.lyn4ever.aop.aopconfig.Teacher;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 声明切面类,也就是包括切点和通知
 */
@Component //声明交由spring管理
@Aspect //表示这是一个切面类
public class AnnotatedAdvice {

    /*
    创建切入点,当然也可以是多个
     */
    @Pointcut("execution(* talk*(..))")
    public void talkExecution(){}

    @Pointcut("bean(high*)")//这里为什么是high,因为我们这回测试bean是highStudent
    public void beanPoint(){}

    @Pointcut("args(value)")
    public void argsPoint(Teacher value){}

    /*
    创建通知,当然也可以是多个
    这个注解的参数就是上边的切入点方法名,注意有的还带参数
    这个通知方法的参数和之前一样,榀加JoinPoint,也可不加
     */
    @Before("talkExecution()")
    public void doSomethingBefore(JoinPoint joinPoint){
        System.out.println("before: Do Something"+joinPoint.getSignature().getName()+"()");
    }

    /**
     * 环绕通知请加上ProceedingJoinPoint参数 ,它是joinPoint的子类
     * 因为你要放行方法的话,必须要加这个
     * @param joinPoint
     * @param teacher
     */
    @Around("argsPoint(teacher) && beanPoint()")
    public Object doSomethindAround(ProceedingJoinPoint joinPoint, Teacher teacher) throws Throwable {
        System.out.println("Around: Before Do Something"+joinPoint.getSignature().getName()+"()");
        Object proceed = joinPoint.proceed();
        System.out.println("Around: After Do Something"+joinPoint.getSignature().getName()+"()");

        return proceed;
    }

}
