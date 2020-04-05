package cn.lyn4ever.service;

import cn.lyn4ever.bean.Store;
import cn.lyn4ever.mapper.StoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.ServiceMode;

/**
 * 微信公众号 “小鱼与Java”
 *
 * @date 2020/4/6
 * @auther Lyn4ever
 */
@Service
public class StoreService {
    @Autowired
    private StoreMapper storeMapper;

    /**
     * 这个注解可用在整个类上和单个方法上
     */
    @Transactional
    public void saveOne(){

        Store store  =new Store();
        store.setTitle("华为P30");

        storeMapper.insertOne(store);

        int j = 10/0;//指定报错，让事务回滚
    }




}

