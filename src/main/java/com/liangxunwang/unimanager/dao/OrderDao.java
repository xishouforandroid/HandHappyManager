package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.Order;
import com.liangxunwang.unimanager.model.Settlement;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/8/21.
 *
 * 后台操作订单
 */
@Repository("orderDao")
public interface OrderDao {

    long count(Map<String, Object> map);

    long countDay(Map<String, Object> map);

    /**
     * 根据订单号
     * @param id
     * @return
     */
    Order findById(String id);

    /**
     * 查询营业额总收入
     * @param map
     * @return
     */
    Float income(Map<String, Object> map);

    /**
     * 查询某一天的结算
     * @param map
     * @return
     */
    Settlement settlement(Map<String, Object> map);

    void updateOrderAccount(Map<String, Object> map);

    //计算消费 收入总额
    String countOrderSum(Map<String, Object> map);
    //买卖商品数量
    String countGoodsSum(Map<String, Object> map);

}
