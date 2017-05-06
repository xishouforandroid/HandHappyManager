package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.model.HappyHandCompany;
import com.liangxunwang.unimanager.model.HappyHandGroup;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.query.PersonQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.util.ControllerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.acl.Group;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Controller
public class AppTuijianController extends ControllerConstants {

    @Autowired
    @Qualifier("appEmpService")
    private ListService appEmpServiceList;

    //推荐人
    @RequestMapping(value = "/appTuijianPeoples", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appTuijianPeoples(String empid, String state, String size, String sex){
        Map<String,String> map = new HashMap<String, String>();
        map.put("empid", empid);
        map.put("state", state);
        map.put("size", size);
        if("0".equals(sex)){
            map.put("sex", "1");
        }
        if("1".equals(sex)){
            map.put("sex", "0");
        }
        try {
            List<Emp> lists = (List<Emp>) appEmpServiceList.list(map);
            DataTip tip = new DataTip();
            tip.setData(lists);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }


    @Autowired
    @Qualifier("appGroupsService")
    private ListService appGroupsServiceList;

    //推荐群
    @RequestMapping(value = "/appTuijianGroups", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appTuijianGroups(String empid){
        try {
            List<HappyHandGroup> lists = (List<HappyHandGroup>) appGroupsServiceList.list(empid);
            DataTip tip = new DataTip();
            tip.setData(lists);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }



    @Autowired
    @Qualifier("appEmpSearchService")
    private ListService appEmpSearchServiceList;
    @RequestMapping(value = "/appSearchPeoplesByKeyWords", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appSearchPeoplesByKeyWords(PersonQuery query){
        try {
            List<Emp> lists = (List<Emp>) appEmpSearchServiceList.list(query);
            DataTip tip = new DataTip();
            tip.setData(lists);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }

}
