package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.AppOrderMakeDao;
import com.liangxunwang.unimanager.dao.CxrzDao;
import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.dao.HyrzDao;
import com.liangxunwang.unimanager.model.*;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.DateUtil;
import com.liangxunwang.unimanager.util.RelativeDateFormat;
import com.liangxunwang.unimanager.util.StringUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/8/14.
 */
@Service("orderUpdateWxService")
public class OrderUpdateWxService implements UpdateService {
    @Autowired
    @Qualifier("appOrderMakeDao")
    private AppOrderMakeDao appOrderMakeSaveDao;

    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;

    @Autowired
    @Qualifier("cxrzDao")
    private CxrzDao cxrzDao;

    //更新订单状态
    @Override
    public Object update(Object object) {
        if (object instanceof Order){
            //跟新主订单和分订单状态
            Order order = (Order) object;
            List<Order> orders = appOrderMakeSaveDao.findOrderByTradeNo(order.getOut_trade_no());
            if(orders != null && orders.size() > 0){
                Order order1 = orders.get(0);
                if(order1 != null){
                    if(!"2".equals(order1.getStatus())){
                        //更新主订单
                        appOrderMakeSaveDao.updateTradeById(order.getOut_trade_no());
                        order.setPay_time(System.currentTimeMillis() + "");
                        //更新分订单
                        appOrderMakeSaveDao.updateOrderById(order);
                        //更新诚信到期日期和诚信认证状态
                        empDao.updateRzstate3(order1.getEmpid(), "1");

                        Map<String, Object> maphyrz = new HashMap<>();
                        maphyrz.put("empid", order1.getEmpid());
                        List<HappyHandCxrz> lists = cxrzDao.lists(maphyrz);
                        if(lists != null && lists.size()>0)
                        {
                            //说明数据库有认证的数据  更新
                            HappyHandCxrz happyHandCxrz =lists.get(0);
                            happyHandCxrz.setIs_use("1");
                            cxrzDao.update(happyHandCxrz);
                        }else{
                            //添加会员认证数据
                            HappyHandCxrz happyHandCxrz = new HappyHandCxrz();
                            happyHandCxrz.setIs_use("1");
                            happyHandCxrz.setStarttime(System.currentTimeMillis() + "");
                            happyHandCxrz.setEmpid(order1.getEmpid());
                            happyHandCxrz.setCxrzid(UUIDFactory.random());
                            cxrzDao.save(happyHandCxrz);
                        }
                    }
                }
            }

        }
        return null;
    }

}
