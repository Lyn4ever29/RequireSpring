package cn.lyn4ever.test;

import cn.lyn4ever.bean.Store;
import cn.lyn4ever.mapper.StoreMapper;
import cn.lyn4ever.serviceaop.StoreServiceAop;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 微信公众号 “小鱼与Java”
 * <p>
 * AOP事务
 *
 * @date 2020/4/5
 * @auther Lyn4ever
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:aoptran.xml") //注意使用的配置文件
public class AOPTranTest {

    @Autowired
    private StoreServiceAop storeServiceAop;


    /**
     * 使用编程式事务
     */
    @Test
    public void fun() {
        storeServiceAop.insertOne();
    }


}
