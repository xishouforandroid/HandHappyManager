package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.EmpGroupsDao;
import com.liangxunwang.unimanager.model.EmpGroups;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("appEmpGroupsListService")
public class AppEmpGroupsListService implements ListService {
    @Autowired
    @Qualifier("empGroupsDao")
    private EmpGroupsDao empGroupsDao;

    @Override
    public Object list(Object object) throws ServiceException {
        String groupId = (String) object;
        Map<String, Object> map = new HashMap<>();
        map.put("groupId", groupId);
        List<EmpGroups> lists = empGroupsDao.lists(map);

        for(EmpGroups happyHandGroup:lists){
            if(!StringUtil.isNullOrEmpty(happyHandGroup.getPic())){
                if (happyHandGroup.getPic().startsWith("upload")) {
                    happyHandGroup.setPic(Constants.URL + happyHandGroup.getPic());
                }else {
                    happyHandGroup.setPic(Constants.QINIU_URL + happyHandGroup.getPic());
                }
            }
            if(!StringUtil.isNullOrEmpty(happyHandGroup.getCover())){
                if (happyHandGroup.getCover().startsWith("upload")) {
                    happyHandGroup.setCover(Constants.URL + happyHandGroup.getCover());
                }else {
                    happyHandGroup.setCover(Constants.QINIU_URL + happyHandGroup.getCover());
                }
            }
        }
        return lists;
    }

}
