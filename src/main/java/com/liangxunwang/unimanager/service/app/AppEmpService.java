package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.chat.impl.EasemobIMUsers;
import com.liangxunwang.unimanager.dao.ChooseDao;
import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.dao.EmpKuDao;
import com.liangxunwang.unimanager.dao.MessagesDao;
import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.model.EmpKu;
import com.liangxunwang.unimanager.model.HappyHandChoose;
import com.liangxunwang.unimanager.model.HappyHandMessage;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.*;
import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.User;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;


@Service("appEmpService")
public class AppEmpService implements ExecuteService,SaveService,UpdateService,ListService,FindService {

    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;

    @Autowired
    @Qualifier("empKuDao")
    private EmpKuDao empKuDao;

    private EasemobIMUsers easemobIMUsers = new EasemobIMUsers();

    @Autowired
    @Qualifier("messagesDao")
    private MessagesDao messagesDao;

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
        //注册成功之后 系统通知
        //todo
        HappyHandMessage happyHandMessage = new HappyHandMessage();
        happyHandMessage.setMsgid(UUIDFactory.random());
        happyHandMessage.setDateline(System.currentTimeMillis() + "");
        happyHandMessage.setTitle("欢迎使用幸福牵手吧APP!\n温馨提示：幸福牵手吧是一个真实的婚恋交友平台，请您真诚交友，祝你早日找到幸福!");
        happyHandMessage.setEmpid(emp.getEmpid());
        messagesDao.save(happyHandMessage);

        HappyHandMessage happyHandMessage1 = new HappyHandMessage();
        happyHandMessage1.setMsgid(UUIDFactory.random());
        happyHandMessage1.setDateline(System.currentTimeMillis() + "");
        happyHandMessage1.setTitle("请使用搜索按钮，查找和添加会员，浏览会员信息!");
        happyHandMessage1.setEmpid(emp.getEmpid());
        messagesDao.save(happyHandMessage1);


//
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
        Map<String, String> map1 = (Map<String, String>) object;
        String empid = map1.get("empid");
        String state = map1.get("state");
        String size = map1.get("size");

        List<Emp> list = new ArrayList<Emp>();
        Emp emp = empDao.findById(empid);
        if(emp == null){
            throw new ServiceException("EmpNull");
        }
        HappyHandChoose happyHandChoose = chooseDao.findByEmpid(empid);
        if(happyHandChoose != null){
            String agestart = happyHandChoose.getAgestart();
            String ageend = happyHandChoose.getAgeend();
            String heightlstart = happyHandChoose.getHeightlstart();
            String heightlend = happyHandChoose.getHeightlend();
            String educationm = happyHandChoose.getEducationm();
            String marriagem = happyHandChoose.getMarriagem();
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("index", 0);
            map.put("size", Integer.parseInt(size));
            map.put("agestart", agestart);
            map.put("ageend", ageend);
            map.put("heightlstart", heightlstart);
            map.put("heightlend", heightlend);
            map.put("educationm", educationm);
            map.put("marriagem", marriagem);
            map.put("state", state);
            list = empDao.listsChoose(map);
            if(list != null){
                for(Emp member:list){
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
                }
            }

        }
        return list;
    }

    @Override
    public Object findById(Object object) throws ServiceException {
        String empid = (String) object;
        Emp member = empDao.findById(empid);
        if(member != null){
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
        }
        return member;
    }
}
