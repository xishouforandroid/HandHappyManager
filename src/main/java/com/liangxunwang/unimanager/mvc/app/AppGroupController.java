package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.model.HappyHandGroup;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.util.ControllerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class AppGroupController extends ControllerConstants {


    @Autowired
    @Qualifier("appGroupsService")
    private FindService appGroupsServiceFind;

    @RequestMapping(value = "/appGroupsById", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appGroupsById(String groupid){
        try {
            HappyHandGroup happyHandGroup = (HappyHandGroup) appGroupsServiceFind.findById(groupid);
            DataTip tip = new DataTip();
            tip.setData(happyHandGroup);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }


    @Autowired
    @Qualifier("appGroupsPublicService")
    private ListService appGroupsPublicServiceList;

    @RequestMapping(value = "/appPublicGroups", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appPublicGroups(){
        try {
            List<HappyHandGroup> lists = (List<HappyHandGroup>) appGroupsPublicServiceList.list("");
            DataTip tip = new DataTip();
            tip.setData(lists);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }
}
