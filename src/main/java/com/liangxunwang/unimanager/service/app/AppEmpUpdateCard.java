package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.dao.EmpKuDao;
import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.model.EmpKu;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service("appEmpUpdateCard")
public class AppEmpUpdateCard implements UpdateService {

    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;

    @Autowired
    @Qualifier("empKuDao")
    private EmpKuDao empKuDao;

    @Override
    public Object update(Object object) {
        Emp emp = (Emp) object;
        if(emp == null){
            throw new ServiceException("null");
        }
        //先根据用户昵称和手机号查询该用户是否和库里的一致
        EmpKu empKu = empKuDao.findByMobile(emp.getMobile());
        if(empKu != null){
            if(!empKu.getNickname().equals(emp.getNickname())){
                throw new ServiceException("empkunull");
            }
        }else {
            throw new ServiceException("empkunull");
        }
        //先查询手机号是否存在
        Emp emp1 = empDao.findByMobile(emp.getMobile());
        if(emp1 == null){
            throw new ServiceException("null");
        }
        empDao.updateCard(emp.getEmpid(), emp.getCardpic());
        return 200;
    }
}
