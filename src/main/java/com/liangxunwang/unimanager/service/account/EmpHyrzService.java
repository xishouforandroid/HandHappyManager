package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.HyrzDao;
import com.liangxunwang.unimanager.model.HappyHandHyrz;
import com.liangxunwang.unimanager.query.EmpQuery;
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
 * Created by zhl on 2015/8/12.
 */
@Service("empHyrzService")
public class EmpHyrzService implements ListService {

    @Autowired
    @Qualifier("hyrzDao")
    private HyrzDao hyrzDao;

    @Override
    public Object list(Object object) throws ServiceException {
        EmpQuery query = (EmpQuery) object;
        Map<String, Object> map = new HashMap<String, Object>();
        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getSize();

        map.put("index", index);
        map.put("size", size);
        if(!StringUtil.isNullOrEmpty(query.getEmpid())){
            map.put("empid", query.getEmpid());
        }
        List<HappyHandHyrz> lists = hyrzDao.lists(map);
        long count = hyrzDao.count(map);
        return new Object[]{lists, count};
    }


}
