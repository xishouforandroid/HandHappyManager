package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.chat.impl.EasemobChatGroup;
import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.dao.EmpGroupsDao;
import com.liangxunwang.unimanager.dao.EmpKuDao;
import com.liangxunwang.unimanager.dao.MessagesDao;
import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.model.EmpGroups;
import com.liangxunwang.unimanager.model.EmpKu;
import com.liangxunwang.unimanager.model.HappyHandMessage;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.*;
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

    @Autowired
    @Qualifier("messagesDao")
    private MessagesDao messagesDao;

    @Autowired
    @Qualifier("empGroupsDao")
    private EmpGroupsDao empGroupsDao;

    private EasemobChatGroup easemobChatGroup = new EasemobChatGroup();

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

        //身份认证成功之后，发送系统消息
        //todo
        HappyHandMessage happyHandMessage = new HappyHandMessage();
        happyHandMessage.setMsgid(UUIDFactory.random());
        happyHandMessage.setDateline(System.currentTimeMillis() + "");
        happyHandMessage.setTitle("恭喜你认证成功!幸福牵手吧为你提供了一个真实的婚恋交友平台，快来体验吧!幸福牵手吧送您一个月的交友体验，时间到期后，体验结束。真心期待您成为我们的会员，我们会努力做得更好!");
        happyHandMessage.setEmpid(emp.getEmpid());
        messagesDao.save(happyHandMessage);

        if(!StringUtil.isNullOrEmpty(emp1.getChannelId())){
            BaiduPush.PushMsgToSingleDevice(Integer.parseInt(emp1.getDeviceType()), "系统消息", "恭喜你认证成功!幸福牵手吧为你提供了一个真实的婚恋交友平台，快来体验吧!幸福牵手吧送您一个月的交友体验，时间到期后，体验结束。真心期待您成为我们的会员，我们会努力做得更好!", "1", emp1.getChannelId());
        }

        //加群
        easemobChatGroup.addSingleUserToChatGroup(Constants.DEFAULT_GROUP_ID1, emp.getEmpid());
        EmpGroups empGroups = new EmpGroups();
        empGroups.setEmpgroupsid(UUIDFactory.random());
        empGroups.setDateline(System.currentTimeMillis() + "");
        empGroups.setGroupid(Constants.DEFAULT_GROUP_ID1);
        empGroups.setEmpid(emp.getEmpid());
        empGroupsDao.save(empGroups);

        HappyHandMessage happyHandMessage1 = new HappyHandMessage();
        happyHandMessage1.setMsgid(UUIDFactory.random());
        happyHandMessage1.setDateline(System.currentTimeMillis() + "");
        happyHandMessage1.setTitle("推荐并欢迎你加入沈阳会员交流群。");
        happyHandMessage1.setEmpid(emp.getEmpid());
        messagesDao.save(happyHandMessage1);

        if(!StringUtil.isNullOrEmpty(emp1.getChannelId())){
            BaiduPush.PushMsgToSingleDevice(Integer.parseInt(emp1.getDeviceType()), "系统消息", "推荐并欢迎你加入沈阳会员交流群。", "1", emp1.getChannelId());
        }

        return 200;
    }
}
