package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.Friends;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.util.ControllerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class AppFriendsController extends ControllerConstants {

    @Autowired
    @Qualifier("appFriendsService")
    private SaveService appFriendsServiceSave;

    @RequestMapping(value = "/appSaveFriends", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appSaveFriends(Friends friends){
        try {
            appFriendsServiceSave.save(friends);
            DataTip tip = new DataTip();
            tip.setData(SUCCESS);
            return toJSONString(tip);
        }catch (Exception e){
            String msg = e.getMessage();
            if (msg.equals("empId1Null")){
                return toJSONString(new ErrorTip(1, "请检查会员ID是否存在！"));
            }else if(msg.equals("empId2Null")){
                return toJSONString(new ErrorTip(1, "请检查会员ID是否存在！"));
            }else if(msg.equals("isFriends")){
                return toJSONString(new ErrorTip(1, "已经是好友了，不能重复添加！"));
            }else if(msg.equals("hasApply")){
                return toJSONString(new ErrorTip(1, "今天已经申请过了！"));
            }else if(msg.equals("applyTooMuch")){
                return toJSONString(new ErrorTip(1, "对该会员的申请次数超过三次了！"));
            }
            else{
                return toJSONString(new ErrorTip(1, "申请失败，请稍后重试！"));
            }
        }
    }



}
