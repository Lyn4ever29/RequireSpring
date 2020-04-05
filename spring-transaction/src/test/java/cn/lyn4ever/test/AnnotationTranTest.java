package cn.lyn4ever.test;

import cn.lyn4ever.service.StoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 微信公众号 “小鱼与Java”
 *
 * 使用注解式的事务
 *
 * @date 2020/4/5
 * @auther Lyn4ever
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:annotation.xml")
public class AnnotationTranTest {

    @Autowired
    private StoreService storeService;

    @Test
    public void fun(){
        storeService.saveOne();

    }


}
