package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.dao.FriendsDao;
import com.liangxunwang.unimanager.dao.MessagesDao;
import com.liangxunwang.unimanager.dao.PhotosDao;
import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.model.Friends;
import com.liangxunwang.unimanager.model.HappyHandMessage;
import com.liangxunwang.unimanager.model.HappyHandPhoto;
import com.liangxunwang.unimanager.query.EmpQuery;
import com.liangxunwang.unimanager.query.FriendsQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/3/3.
 */
@Service("appFriendsService")
public class AppFriendsService implements SaveService,ListService,UpdateService,ExecuteService {
    @Autowired
    @Qualifier("friendsDao")
    private FriendsDao friendsDao;

    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;

    @Autowired
    @Qualifier("messagesDao")
    private MessagesDao messagesDao;


    @Override
    public Object save(Object object) throws ServiceException {
        Friends friends = (Friends) object;
        if(StringUtil.isNullOrEmpty(friends.getEmpid1())){
            throw new ServiceException("empId1Null");
        }
        if(StringUtil.isNullOrEmpty(friends.getEmpid2())){
            throw new ServiceException("empId2Null");
        }
        //先判断是否存在好友关系
        Map<String, Object> map = new HashMap<>();
        map.put("empid1", friends.getEmpid1() );
        map.put("empid2", friends.getEmpid2() );
        map.put("is_check", "1" );
        List<Friends> list1 = friendsDao.lists(map);
        if(list1 != null && list1.size() > 0){
            throw new ServiceException("isFriends");//已经是好友了
        }

        //一天只能发一次加好友请求
        Map<String, Object> map1 = new HashMap<>();
        map1.put("empid1", friends.getEmpid1());
        map1.put("empid2", friends.getEmpid2());

        map1.put("starttime",  DateUtil.getStartDay());
        map1.put("endtime",  DateUtil.getEndDay());

        long l1= friendsDao.count(map1);
        if(l1 > 0){
            throw new ServiceException("hasApply");//今天已经申请过了
        }

        //一共是三次请求加好友机会
        Map<String, Object> map2 = new HashMap<>();
        map2.put("empid1", friends.getEmpid1());
        map2.put("empid2", friends.getEmpid2());
        long l2 = friendsDao.count(map2);
        if(l2 > 2){
            throw new ServiceException("applyTooMuch");//申请超过三次
        }

        friends.setApplytime(System.currentTimeMillis()+"");
        friends.setIs_check("0");
        friends.setFriendsid(UUIDFactory.random());
        friendsDao.save(friends);

        Emp emp2= empDao.findById(friends.getEmpid2());
        if(!StringUtil.isNullOrEmpty(emp2.getChannelId())){
            BaiduPush.PushMsgToSingleDevice(Integer.parseInt(emp2.getDeviceType()), "好友请求", "有新的好友请求，点此查看", "5", emp2.getChannelId());
        }

        return null;
    }

    @Override
    public Object list(Object object) throws ServiceException {
        FriendsQuery query = (FriendsQuery) object;
        //先判断是否存在好友关系
        Map<String, Object> map = new HashMap<>();
        if(!StringUtil.isNullOrEmpty(query.getEmpid1())){
            map.put("empid1", query.getEmpid1());
        }
        if(!StringUtil.isNullOrEmpty(query.getEmpid2())){
            map.put("empid2", query.getEmpid2());
        }
        if(!StringUtil.isNullOrEmpty(query.getIs_check())){
            map.put("is_check", query.getIs_check());
        }
        List<Friends> list = friendsDao.lists(map);
        for(Friends member:list){
            if(member != null){
                if(!StringUtil.isNullOrEmpty(member.getEmpid1Cover())){
                    if (member.getEmpid1Cover().startsWith("upload")) {
                        member.setEmpid1Cover(Constants.URL + member.getEmpid1Cover());
                    }else {
                        member.setEmpid1Cover(Constants.QINIU_URL + member.getEmpid1Cover());
                    }
                }
                if(!StringUtil.isNullOrEmpty(member.getEmpid2Cover())){
                    if (member.getEmpid2Cover().startsWith("upload")) {
                        member.setEmpid2Cover(Constants.URL + member.getEmpid2Cover());
                    }else {
                        member.setEmpid2Cover(Constants.QINIU_URL + member.getEmpid2Cover());
                    }
                }
            }
        }
        return list;
    }


    //接受好友申请
    @Override
    public Object update(Object object) {
        Friends friends = (Friends) object;
        if(StringUtil.isNullOrEmpty(friends.getIs_check())){
            throw new ServiceException("ischecknull");
        }
        if(StringUtil.isNullOrEmpty(friends.getFriendsid())){
            throw new ServiceException("friendsidnull");
        }
        friends.setAccepttime(System.currentTimeMillis()+"");
        friendsDao.update(friends);
        if("1".equals(friends.getIs_check())){
            //说明接受申请了
            Friends friends1 = new Friends();
            friends1.setFriendsid(UUIDFactory.random());
            friends1.setIs_check("1");
            friends1.setAccepttime(System.currentTimeMillis() + "");
            friends1.setApplytime(System.currentTimeMillis()+"");
            friends1.setEmpid1(friends.getEmpid2());
            friends1.setEmpid2(friends.getEmpid1());
            friendsDao.save(friends1);

            //添加系统消息
            Emp emp1= empDao.findById(friends.getEmpid1());
            Emp emp2= empDao.findById(friends.getEmpid2());

            HappyHandMessage happyHandMessage = new HappyHandMessage();
            happyHandMessage.setMsgid(UUIDFactory.random());
            happyHandMessage.setDateline(System.currentTimeMillis() + "");
            happyHandMessage.setTitle(emp2.getNickname()+"已经同意你的好友请求");
            happyHandMessage.setEmpid(emp1.getEmpid());
            messagesDao.save(happyHandMessage);

            if(!StringUtil.isNullOrEmpty(emp1.getChannelId())){
                BaiduPush.PushMsgToSingleDevice(Integer.parseInt(emp1.getDeviceType()), "系统消息", emp2.getNickname()+"已经同意你的好友请求", "1", emp1.getChannelId());
            }
        }else{
            //说明拒绝了
            Emp emp1= empDao.findById(friends.getEmpid1());
            Emp emp2= empDao.findById(friends.getEmpid2());

            HappyHandMessage happyHandMessage = new HappyHandMessage();
            happyHandMessage.setMsgid(UUIDFactory.random());
            happyHandMessage.setDateline(System.currentTimeMillis() + "");
            happyHandMessage.setTitle(emp2.getNickname()+"已经拒绝你的好友请求");
            happyHandMessage.setEmpid(emp1.getEmpid());
            messagesDao.save(happyHandMessage);

            if(!StringUtil.isNullOrEmpty(emp1.getChannelId())){
                BaiduPush.PushMsgToSingleDevice(Integer.parseInt(emp1.getDeviceType()), "系统消息", emp2.getNickname()+"已经拒绝你的好友请求", "1", emp1.getChannelId());
            }
        }
        return 200;
    }

    @Override
    public Object execute(Object object) throws Exception {
        Friends friends = (Friends) object;
        if(StringUtil.isNullOrEmpty(friends.getEmpid1())){
            throw new ServiceException("empidisnull");
        }
        if(StringUtil.isNullOrEmpty(friends.getEmpid2())){
            throw new ServiceException("empidisnull");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("empid1" ,friends.getEmpid1());
        map.put("empid2" ,friends.getEmpid2());
        map.put("is_check" ,"1");

        List<Friends> listss = friendsDao.lists(map);
        if(listss != null && listss.size()>0){
            friendsDao.delete(friends);
        }else {
            throw new ServiceException("noexist");
        }
        return 200;
    }
}
