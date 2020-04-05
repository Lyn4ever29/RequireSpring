package cn.lyn4ever.test;

import cn.lyn4ever.bean.Store;
import cn.lyn4ever.mapper.StoreMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * 微信公众号 “小鱼与Java”
 *
 * 使用注解式的事务
 *
 * @date 2020/4/5
 * @auther Lyn4ever
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:codetran.xml") //注意使用的配置文件
public class CodeTranTest {

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private  TransactionTemplate transactionTemplate;

    /**
     * 使用编程式事务
     */
    @Test
    public void fun() {
        transactionTemplate.execute(txStatus -> {
            Store store = new Store();
            store.setTitle("小米11");
            storeMapper.insertOne(store);
            //制造错误，让事务回滚
            int i = 10 / 0;
            return null;
        });
    }


}
