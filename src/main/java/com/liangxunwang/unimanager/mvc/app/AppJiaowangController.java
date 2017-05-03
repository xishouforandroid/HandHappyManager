package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.Friends;
import com.liangxunwang.unimanager.model.HappyHandJw;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.query.FriendsQuery;
import com.liangxunwang.unimanager.query.JiaowangQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.ControllerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class AppJiaowangController extends ControllerConstants {

    @Autowired
    @Qualifier("appJiaowangService")
    private SaveService appJiaowangServiceSave;

    @Autowired
    @Qualifier("appJiaowangService")
    private ListService appJiaowangServiceList;

    @Autowired
    @Qualifier("appJiaowangService")
    private UpdateService appJiaowangServiceUpdate;

    @Autowired
    @Qualifier("appJiaowangService")
    private DeleteService appJiaowangServiceDelete;

    @Autowired
    @Qualifier("appJiaowangService")
    private ExecuteService appJiaowangServiceExe;


    @RequestMapping(value = "/appSaveJiaowang", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appSaveJiaowang(HappyHandJw happyHandJw){
        try {
            appJiaowangServiceSave.save(happyHandJw);
            DataTip tip = new DataTip();
            tip.setData(SUCCESS);
            return toJSONString(tip);
        }catch (Exception e){
            String msg = e.getMessage();
            if (msg.equals("empId1Null")){
                return toJSONString(new ErrorTip(1, "请检查会员ID是否存在！"));
            }else if(msg.equals("empId2Null")){
                return toJSONString(new ErrorTip(1, "请检查会员ID是否存在！"));
            }else if(msg.equals("youHasJwdx")){
                return toJSONString(new ErrorTip(1, "您已经有交往对象了，不能重复添加！"));
            }else if(msg.equals("sheHasJwdx")){
                return toJSONString(new ErrorTip(1, "对方已经有交往对象了，不能重复添加！"));
            }else if(msg.equals("hasApply")){
                return toJSONString(new ErrorTip(2, "对方尚未确认，请耐心等待"));
            }
            else{
                return toJSONString(new ErrorTip(1, "申请失败，请稍后重试！"));
            }
        }
    }

    @RequestMapping(value = "/appJiaowangs", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appJiaowangs(JiaowangQuery query){
        try {
            List<HappyHandJw> lists = (List<HappyHandJw>) appJiaowangServiceList.list(query);
            DataTip tip = new DataTip();
            tip.setData(lists);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }


    //处理交往申请
    @RequestMapping(value = "/appAcceptJiaowang", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appAcceptJiaowang(HappyHandJw happyHandJw){
        try {
            appJiaowangServiceUpdate.update(happyHandJw);
            DataTip tip = new DataTip();
            tip.setData(SUCCESS);
            return toJSONString(tip);
        }catch (Exception e){
            String msg = e.getMessage();
            if (msg.equals("jwidisnull")){
                return toJSONString(new ErrorTip(1, "请检查该申请ID是否存在！"));
            }else if(msg.equals("ischecknull")){
                return toJSONString(new ErrorTip(1, "请确认是否传值正确！"));
            }else if(msg.equals("sheHasJwdx")){
                return toJSONString(new ErrorTip(1, "对方已经有交往对象了！请拒绝！"));
            }else if(msg.equals("youHasJwdx")){
                return toJSONString(new ErrorTip(1, "您已经有交往对象了！请拒绝！"));
            }
            else{
                return toJSONString(new ErrorTip(1, "操作失败，请稍后重试！"));
            }
        }
    }


    //取消邀请交往请求
    @RequestMapping(value = "/appDeleteJiaowang", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appDeleteJiaowang(HappyHandJw happyHandJw){
        try {
            appJiaowangServiceDelete.delete(happyHandJw);
            DataTip tip = new DataTip();
            tip.setData(SUCCESS);
            return toJSONString(tip);
        }catch (Exception e){
            String msg = e.getMessage();
            return toJSONString(new ErrorTip(1, "操作失败，请稍后重试！"));
        }
    }


    //删除交往对象 解除交往关系
    @RequestMapping(value = "/appDeleteJiaowangDx", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appDeleteJiaowangDx(HappyHandJw happyHandJw){
        try {
            appJiaowangServiceExe.execute(happyHandJw);
            DataTip tip = new DataTip();
            tip.setData(SUCCESS);
            return toJSONString(tip);
        }catch (Exception e){
            String msg = e.getMessage();
            return toJSONString(new ErrorTip(1, "操作失败，请稍后重试！"));
        }
    }
}
