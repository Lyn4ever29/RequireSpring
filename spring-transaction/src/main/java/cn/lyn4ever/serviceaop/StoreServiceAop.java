package cn.lyn4ever.serviceaop;

import cn.lyn4ever.bean.Store;
import cn.lyn4ever.mapper.StoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 微信公众号 “小鱼与Java”
 * 这个类是被AOP进行事务管理的
 *
 * @date 2020/4/6
 * @auther Lyn4ever
 */
@Service
public class StoreServiceAop {

    @Autowired
    private StoreMapper storeMapper;

    /**
     * 这个方法是
     */
    public void insertOne(){

        Store store  =new Store();
        store.setTitle("华为P30");

        storeMapper.insertOne(store);

        int j = 10/0;//指定报错，让事务回滚
    }
}
