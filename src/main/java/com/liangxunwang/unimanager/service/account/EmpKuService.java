package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.EmpKuDao;
import com.liangxunwang.unimanager.model.EmpKu;
import com.liangxunwang.unimanager.query.EmpQuery;
import com.liangxunwang.unimanager.service.DeleteService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.StringUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/8/12.
 */
@Service("empKuService")
public class EmpKuService implements ListService,SaveService,DeleteService {

    @Autowired
    @Qualifier("empKuDao")
    private EmpKuDao empKuDao;

    @Override
    public Object list(Object object) throws ServiceException {
        EmpQuery query = (EmpQuery) object;
        Map<String, Object> map = new HashMap<String, Object>();
        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getSize();

        map.put("index", index);
        map.put("size", size);

        List<EmpKu> lists = empKuDao.lists(map);
        long count = empKuDao.count(map);

        return new Object[]{lists, count};
    }


    @Override
    public Object save(Object object) throws ServiceException {
        EmpKu emp = (EmpKu) object;
        if(emp != null){
            //先检查姓名和手机号是否已经存在了
            if(!StringUtil.isNullOrEmpty(emp.getMobile())){
                EmpKu empKu = empKuDao.findByMobile(emp.getMobile());
                if(empKu != null){
                    //说明已经存在了
                    throw new ServiceException("HAS_EXIST_MOBILE");
                }
            }else {
                throw new ServiceException("MOBILE_NULL");
            }
        }else {
            throw new ServiceException("EMP_NULL");
        }
        emp.setEmpkuid(UUIDFactory.random());
        emp.setDateline(System.currentTimeMillis()+"");
        empKuDao.add(emp);
        return 200;
    }


    @Override
    public Object delete(Object object) throws ServiceException {
        String id = (String) object;
        empKuDao.delete(id);
        return 200;
    }
}
