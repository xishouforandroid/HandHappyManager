package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.FriendsDao;
import com.liangxunwang.unimanager.dao.PhotosDao;
import com.liangxunwang.unimanager.model.Friends;
import com.liangxunwang.unimanager.model.HappyHandPhoto;
import com.liangxunwang.unimanager.query.EmpQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
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
public class AppFriendsService implements SaveService {
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
}
