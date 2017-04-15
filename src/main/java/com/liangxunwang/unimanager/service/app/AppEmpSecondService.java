package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.ChooseDao;
import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.model.HappyHandChoose;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.StringUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


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

        if(emp == null){
            throw new ServiceException("null");
        }
        if(StringUtil.isNullOrEmpty(emp.getEmpid())){
            throw new ServiceException("empidnull");
        }
        empDao.updateProfile(emp);
        if(!StringUtil.isNullOrEmpty(emp.getChooseid())){
            //更新
            HappyHandChoose happyHandChoose = new HappyHandChoose();
            happyHandChoose.setChooseid(emp.getChooseid());
            happyHandChoose.setAgestart(emp.getAgestart());
            happyHandChoose.setAgeend(emp.getAgeend());
            happyHandChoose.setHeightlstart(emp.getHeightlstart());
            happyHandChoose.setHeightlend(emp.getHeightlend());
            happyHandChoose.setEducationm(emp.getEducationm());
            happyHandChoose.setMarriagem(emp.getMarriagem());
            chooseDao.update(happyHandChoose);
        }else {
            //新增
            HappyHandChoose happyHandChoose = new HappyHandChoose();
            happyHandChoose.setChooseid(UUIDFactory.random());
            happyHandChoose.setEmpid(emp.getEmpid());
            happyHandChoose.setAgestart(emp.getAgestart());
            happyHandChoose.setAgeend(emp.getAgeend());
            happyHandChoose.setHeightlstart(emp.getHeightlstart());
            happyHandChoose.setHeightlend(emp.getHeightlend());
            happyHandChoose.setEducationm(emp.getEducationm());
            happyHandChoose.setMarriagem(emp.getMarriagem());
            chooseDao.save(happyHandChoose);
        }
        Emp emp1 = empDao.findById(emp.getEmpid());
        if(!StringUtil.isNullOrEmpty(emp1.getCover())){
            if (emp1.getCover().startsWith("upload")) {
                emp1.setCover(Constants.URL + emp1.getCover());
            }else {
                emp1.setCover(Constants.QINIU_URL + emp1.getCover());
            }
        }
        return emp1;
    }
}
