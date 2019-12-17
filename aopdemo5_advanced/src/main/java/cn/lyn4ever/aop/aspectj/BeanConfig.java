package cn.lyn4ever.aop.aspectj;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration //声明这是一个配置类
@ComponentScan("cn.lyn4ever.aop.aspectj")
@EnableAspectJAutoProxy(proxyTargetClass = true)//相当于xml中的<aop:aspectj-autoproxy/>
public class BeanConfig {
}
