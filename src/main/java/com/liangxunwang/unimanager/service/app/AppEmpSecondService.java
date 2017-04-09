package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.chat.impl.EasemobIMUsers;
import com.liangxunwang.unimanager.dao.ChooseDao;
import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.dao.EmpKuDao;
import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.model.EmpKu;
import com.liangxunwang.unimanager.model.HappyHandChoose;
import com.liangxunwang.unimanager.service.ExecuteService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.MD5Util;
import com.liangxunwang.unimanager.util.StringUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.User;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service("appEmpSecondService")
public class AppEmpSecondService implements UpdateService {

    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;

    @Autowired
    @Qualifier("chooseDao")
    private ChooseDao chooseDao;

    @Override
    public Object update(Object object) {
        Emp emp = (Emp) object;
        HappyHandChoose happyHandChoose = new HappyHandChoose();
        if(emp == null){
            throw new ServiceException("null");
        }
        if(StringUtil.isNullOrEmpty(emp.getEmpid())){
            throw new ServiceException("empidnull");
        }
        empDao.updateProfile(emp);
        happyHandChoose.setChooseid(UUIDFactory.random());
        happyHandChoose.setEmpid(emp.getEmpid());
        happyHandChoose.setAgestart(emp.getAgestart());
        happyHandChoose.setAgeend(emp.getAgeend());
        happyHandChoose.setHeightlstart(emp.getHeightlstart());
        happyHandChoose.setHeightlend(emp.getHeightlend());
        happyHandChoose.setEducationm(emp.getEducationm());
        happyHandChoose.setMarriagem(emp.getMarriagem());
        chooseDao.save(happyHandChoose);
        return emp;
    }
}
