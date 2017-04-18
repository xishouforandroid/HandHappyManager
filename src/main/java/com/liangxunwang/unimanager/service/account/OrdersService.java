package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.AppOrderMakeDao;
import com.liangxunwang.unimanager.mvc.vo.OrderVO;
import com.liangxunwang.unimanager.query.LikesQuery;
import com.liangxunwang.unimanager.query.OrderQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/3/3.
 */
@Service("ordersService")
public class OrdersService implements ListService {
    @Autowired
    @Qualifier("appOrderMakeDao")
    private AppOrderMakeDao appOrderMakeDao;

    @Override
    public Object list(Object object) throws ServiceException {
        OrderQuery query = (OrderQuery) object;
        Map<String, Object> map = new HashMap<String, Object>();
        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getSize();

        map.put("index", index);
        map.put("size", size);

        if(!StringUtil.isNullOrEmpty(query.getEmpid())){
            map.put("empid", query.getEmpid());
        }
        if(!StringUtil.isNullOrEmpty(query.getStatus())){
            map.put("status", query.getStatus());
        }

        if(!StringUtil.isNullOrEmpty(query.getPay_status())){
            map.put("pay_status", query.getPay_status());
        }

        if(!StringUtil.isNullOrEmpty(query.getIs_dxk_order())){
            map.put("is_dxk_order", query.getIs_dxk_order());
        }

        if(!StringUtil.isNullOrEmpty(query.getTrade_type())){
            map.put("trade_type", query.getTrade_type());
        }

        List<OrderVO> lists = appOrderMakeDao.lists(map);
        long count = appOrderMakeDao.count(map);

        return new Object[]{lists, count};
    }



}
