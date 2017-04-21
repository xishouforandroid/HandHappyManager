package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.FriendsDao;
import com.liangxunwang.unimanager.dao.PhotosDao;
import com.liangxunwang.unimanager.model.Friends;
import com.liangxunwang.unimanager.model.HappyHandPhoto;
import com.liangxunwang.unimanager.query.EmpQuery;
import com.liangxunwang.unimanager.query.FriendsQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.service.UpdateService;
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
public class AppFriendsService implements SaveService,ListService,UpdateService {
    @Autowired
    @Qualifier("friendsDao")
    private FriendsDao friendsDao;


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
        }
        return 200;
    }

}
