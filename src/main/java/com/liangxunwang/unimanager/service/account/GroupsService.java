package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.chat.impl.EasemobChatGroup;
import com.liangxunwang.unimanager.dao.GroupsDao;
import com.liangxunwang.unimanager.model.HappyHandGroup;
import com.liangxunwang.unimanager.query.GroupsQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.StringUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import io.swagger.client.model.Group;
import org.aspectj.apache.bcel.classfile.Constant;
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
        group.groupname(happyHandGroup.getTitle()).desc(happyHandGroup.getContent())._public(true).maxusers(2000).approval(false).owner(Constants.DEFAULT_GROUP_OWNER);
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
        HappyHandGroup happyHandGroup = (HappyHandGroup) object;
        groupsDao.update(happyHandGroup);
        return 200;
    }

    @Override
    public Object findById(Object object) throws ServiceException {
        return groupsDao.findByLikeId((String) object);
    }
}
