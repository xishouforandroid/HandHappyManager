package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.model.HappyHandCompany;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
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
import java.util.Objects;


@Controller
public class AppTuijianController extends ControllerConstants {

    @Autowired
    @Qualifier("appEmpService")
    private ListService appEmpServiceList;

    @RequestMapping(value = "/appTuijianPeoples", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appTuijianPeoples(String empid, String state, String size){
        Map<String,String> map = new HashMap<String, String>();
        map.put("empid", empid);
        map.put("state", state);
        map.put("size", size);
        try {
            List<Emp> lists = (List<Emp>) appEmpServiceList.list(map);
            DataTip tip = new DataTip();
            tip.setData(lists);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }



}
