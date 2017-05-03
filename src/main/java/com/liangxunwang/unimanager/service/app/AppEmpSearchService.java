package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.query.PersonQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("appEmpSearchService")
public class AppEmpSearchService implements ListService {

    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;

    @Override
    public Object list(Object object) throws ServiceException {
        PersonQuery query = (PersonQuery) object;
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("index", 0);
        map.put("size", 5);
        if(!StringUtil.isNullOrEmpty(query.getSex())){
            if("0".equals(query.getSex())){
                map.put("sex", "1");
            }else{
                map.put("sex", "0");
            }
        }
        if(!StringUtil.isNullOrEmpty(query.getKeywords())){
            map.put("keywords", query.getKeywords());
        }
        if(!StringUtil.isNullOrEmpty(query.getAgestart())){
            map.put("agestart", query.getAgestart());
        }
        if(!StringUtil.isNullOrEmpty(query.getAgeend())){
            map.put("ageend", query.getAgeend());
        }
        if(!StringUtil.isNullOrEmpty(query.getHeightlstart())){
            map.put("heightlstart", query.getHeightlstart());
        }
        if(!StringUtil.isNullOrEmpty(query.getHeightlend())){
            map.put("heightlend", query.getHeightlend());
        }
        if(!StringUtil.isNullOrEmpty(query.getEducationID2())){
            map.put("educationm", query.getEducationID2());
        }
        if(!StringUtil.isNullOrEmpty(query.getMarragieID())){
            map.put("marriagem", query.getMarragieID());
        }
        if(!StringUtil.isNullOrEmpty(query.getLikeids())){
            map.put("likeids", query.getLikeids());
        }
        map.put("state", "1");
        List<Emp> list1 = empDao.listsSearch(map);
        map.put("state", "2");
        List<Emp> list2 = empDao.listsSearch(map);

        List<Emp> list = new ArrayList<>();

        if(list1 != null){
            for(Emp member:list1){
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
                list.add(member);
            }
        }
        if(list2 != null){
            for(Emp member:list2){
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
                list.add(member);
            }
        }
        return list;
    }

}
