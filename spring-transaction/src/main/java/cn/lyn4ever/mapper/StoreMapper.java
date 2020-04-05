package cn.lyn4ever.mapper;

import cn.lyn4ever.bean.Store;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

/**
 * mybatis接口
 * 微信公众号 “小鱼与Java”
 */
public interface StoreMapper {
    @Insert("insert into store (title) values(#{title})")
    int insertOne(Store store);

    @Update("update store set title=#{title} where id=#{id}")
    int updateOne(Store store);

}
