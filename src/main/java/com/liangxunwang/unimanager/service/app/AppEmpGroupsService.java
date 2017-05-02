package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.chat.impl.EasemobChatGroup;
import com.liangxunwang.unimanager.dao.EmpGroupsDao;
import com.liangxunwang.unimanager.model.EmpGroups;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.StringUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/3/3.
 */
@Service("appEmpGroupsService")
public class AppEmpGroupsService implements ListService,SaveService,FindService {
    @Autowired
    @Qualifier("empGroupsDao")
    private EmpGroupsDao empGroupsDao;

    private EasemobChatGroup easemobChatGroup = new EasemobChatGroup();


    @Override
    public Object list(Object object) throws ServiceException {
        String empid = (String) object;
        Map<String, Object> map = new HashMap<>();
        map.put("empid", empid);
        List<EmpGroups> lists = empGroupsDao.lists(map);

        for(EmpGroups happyHandGroup:lists){
            if(!StringUtil.isNullOrEmpty(happyHandGroup.getPic())){
                if (happyHandGroup.getPic().startsWith("upload")) {
                    happyHandGroup.setPic(Constants.URL + happyHandGroup.getPic());
                }else {
                    happyHandGroup.setPic(Constants.QINIU_URL + happyHandGroup.getPic());
                }
            }
        }
        return lists;
    }

    @Override
    public Object save(Object object) throws ServiceException {
        EmpGroups empGroups = (EmpGroups) object;
        empGroups.setEmpgroupsid(UUIDFactory.random());
        empGroups.setDateline(System.currentTimeMillis()+"");
        empGroupsDao.save(empGroups);
        //环信加入群聊
        easemobChatGroup.addSingleUserToChatGroup(empGroups.getGroupid(), empGroups.getEmpid());
        return 200;
    }

    @Override
    public Object findById(Object object) throws ServiceException {
        EmpGroups empGroups = (EmpGroups) object;
        Map<String, Object> map = new HashMap<>();
        map.put("empid", empGroups.getEmpid());
        map.put("groupid", empGroups.getGroupid());
        List<EmpGroups> lists = empGroupsDao.findById(map);
        if(lists != null && lists.size()>0){
            return true;
        }else{
            return false;
        }
    }
}
