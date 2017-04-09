package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.chat.impl.EasemobChatGroup;
import com.liangxunwang.unimanager.dao.GroupsDao;
import com.liangxunwang.unimanager.model.HappyHandGroup;
import com.liangxunwang.unimanager.query.GroupsQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.StringUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import io.swagger.client.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/3/3.
 */
@Service("groupsService")
public class GroupsService implements ListService,SaveService,DeleteService,ExecuteService,UpdateService,FindService {
    @Autowired
    @Qualifier("groupsDao")
    private GroupsDao groupsDao;

    private EasemobChatGroup easemobChatGroup = new EasemobChatGroup();

    @Override
    public Object list(Object object) throws ServiceException {
        GroupsQuery query = (GroupsQuery) object;
        Map<String, Object> map = new HashMap<String, Object>();
        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getSize();

        map.put("index", index);
        map.put("size", size);

        if(!StringUtil.isNullOrEmpty(query.getIs_use())){
            map.put("is_use", query.getIs_use());
        }
        if(!StringUtil.isNullOrEmpty(query.getLikeid())){
            map.put("likeid", query.getLikeid());
        }

        List<HappyHandGroup> lists = groupsDao.lists(map);
        long count = groupsDao.count(map);

        return new Object[]{lists, count};
    }


    @Override
    public Object save(Object object) throws ServiceException {
        HappyHandGroup happyHandGroup = (HappyHandGroup) object;
        happyHandGroup.setGroupid(UUIDFactory.random());
        happyHandGroup.setIs_use("1");
        groupsDao.save(happyHandGroup);
        //添加群组成功，在环信添加群组
        Group group = new Group();
//        "groupname":"testrestgrp12", //群组名称，此属性为必须的
//         "desc":"server create group", //群组描述，此属性为必须的
//                "public":true, //是否是公开群，此属性为必须的
//                "maxusers":300, //群组成员最大数（包括群主），值为数值类型，默认值200，最大值2000，此属性为可选的
//                "approval":true, //加入公开群是否需要批准，默认值是false（加入公开群不需要群主批准），此属性为必选的，私有群必须为true
//                "owner":"jma1", //群组的管理员，此属性为必须的
//                "members":["jma2","jma3"] //群组成员，此属性为可选的，但是如果加了此项，数组元素至少一个（注：群主jma1不需要写入到members里面）
        group.groupname(happyHandGroup.getTitle()).desc(happyHandGroup.getContent())._public(true).maxusers(2000).approval(false).owner("0aca2b9b12cd4b73ad79426ddd28cee5");
        Object result = easemobChatGroup.createChatGroup(group);
        return null;
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        String likeid = (String) object;
        groupsDao.delete(likeid);
        return null;
    }

    @Override
    public Object execute(Object object) throws ServiceException {
        return groupsDao.findById((String) object);
    }

    @Override
    public Object update(Object object) {
        HappyHandGroup happyHandLike = (HappyHandGroup) object;
        groupsDao.update(happyHandLike);
        return 200;
    }

    @Override
    public Object findById(Object object) throws ServiceException {
        return groupsDao.findByLikeId((String) object);
    }
}
