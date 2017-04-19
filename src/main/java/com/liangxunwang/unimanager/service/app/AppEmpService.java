package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.chat.impl.EasemobIMUsers;
import com.liangxunwang.unimanager.dao.ChooseDao;
import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.dao.EmpKuDao;
import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.model.EmpKu;
import com.liangxunwang.unimanager.model.HappyHandChoose;
import com.liangxunwang.unimanager.service.*;
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


@Service("appEmpService")
public class AppEmpService implements ExecuteService,SaveService,UpdateService,ListService {

    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;

    @Autowired
    @Qualifier("empKuDao")
    private EmpKuDao empKuDao;

    private EasemobIMUsers easemobIMUsers = new EasemobIMUsers();

    @Override
    public Object execute(Object object) throws ServiceException {
        Object[] params = (Object[]) object;
        String mobile = (String) params[0];
        String password = (String) params[1];
        Emp member = empDao.findByMobile(mobile);
        if (member == null){
            throw new ServiceException("NotFound");
        }
        if(StringUtil.isNullOrEmpty(member.getPassword())){
            //密码为空
            throw new ServiceException("PassNull");
        }
        if (!new MD5Util().getMD5ofStr(password).equals(member.getPassword())){
            throw new ServiceException("PassError");
        }
        if ("0".equals(member.getIs_use())){
            throw new ServiceException("NotUse");
        }
        if(!StringUtil.isNullOrEmpty(member.getCover())){
            if (member.getCover().startsWith("upload")) {
                member.setCover(Constants.URL + member.getCover());
            }else {
                member.setCover(Constants.QINIU_URL + member.getCover());
            }
        }
        if(!StringUtil.isNullOrEmpty(member.getCardpic())){
            if (member.getCardpic().startsWith("upload")) {
                member.setCardpic(Constants.URL + member.getCardpic());
            }else {
                member.setCardpic(Constants.QINIU_URL + member.getCardpic());
            }
        }
        return member;
    }

    @Override
    public Object save(Object object) throws ServiceException {
        Emp emp = (Emp) object;
        //根据mobile查询库里面是否存在改手机号码
        EmpKu empKu = empKuDao.findByMobile(emp.getMobile());
        if(empKu == null){
            throw new ServiceException("MobileNotFound");
        }
        //查询该手机号是否已经注册
        Emp emp1 = empDao.findByMobile(emp.getMobile());
        if(emp1 != null){
            throw new ServiceException("MobileHasExist");
        }
        emp.setEmpid(UUIDFactory.random());
        emp.setDateline(System.currentTimeMillis() + "");
        emp.setIs_use("2");//0否 1是 2尚未维护资料
        if(!StringUtil.isNullOrEmpty(emp.getPassword())){
            emp.setPassword(new MD5Util().getMD5ofStr(emp.getPassword()));//密码加密
        }
        empDao.save(emp);
        //同步在环信注册该用户

        RegisterUsers users = new RegisterUsers();
        User user = new User().username(emp.getEmpid() + new Random().nextInt(500)).password("123456");
        users.add(user);
        Object result = easemobIMUsers.createNewIMUserSingle(users);
        Assert.assertNotNull(result);
        return emp.getEmpid();
    }

    @Override
    public Object update(Object object) {
        Emp emp = (Emp) object;
        if(!StringUtil.isNullOrEmpty(emp.getEmpid()) && !StringUtil.isNullOrEmpty(emp.getCover())){
            //更新头像
            empDao.updateCover(emp.getEmpid(), emp.getCover());
        }
        return 200;
    }


    @Autowired
    @Qualifier("chooseDao")
    private ChooseDao chooseDao;

    //查询推荐人
    @Override
    public Object list(Object object) throws ServiceException {
        String empid = (String) object;//登录人的ID
        Emp emp = empDao.findById(empid);
        if(emp == null){
            throw new ServiceException("EmpNull");
        }
        HappyHandChoose happyHandChoose = chooseDao.findByEmpid(empid);

        return null;
    }
}
