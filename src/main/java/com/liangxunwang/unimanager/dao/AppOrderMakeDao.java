package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.Order;
import com.liangxunwang.unimanager.model.ShoppingTrade;
import com.liangxunwang.unimanager.mvc.vo.OrderVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/8/10.
 */
@Repository("appOrderMakeDao")
public interface AppOrderMakeDao {
    /**
     * 保存订单
     */
    void saveList(Order order);
    //查询该会员是否刷过今天的定向卡订单了
    Order findIsExist(Map<String, Object> map);
    /**
     * �保存订单 交易号  和支付宝关联��
     * */
    void saveTrade(ShoppingTrade shoppingTrade);

    //更新订单
    void updateOrderById(Order order);
    void updateOrderByTradeId(Order order);
    void updateTradeById(String out_trade_no);

    //取消订单
    void cancelOrderById(String order_no);

    //确认收货
    void sureOrder(Order order);

    void updateOrderByOrderNo(Order order);

    //更新订单--单一的分订单--去付款
    void updateOrderBySingle(@Param(value = "order_no") String order_no, @Param(value = "pay_time") String pay_time);

    //查询今日订单总数量
    long selectOrderNumByDay(Map<String, Object> map);

    //查询总的订单数量
    long selectOrderNum(Map<String, Object> map);

    //查询总的收入
    String selectSum(Map<String, Object> map);

    //删除订单
    void deleteOrderById(String order_no);
    //根据交易号查询订单列表
    List<Order> findOrderByTradeNo(String out_trade_no);

    //根据订单号查询订单状态
    Order findOrderByOrderNo(String order_no);

    List<OrderVO> lists(Map<String, Object> map);
    long count(Map<String, Object> map);
 }
