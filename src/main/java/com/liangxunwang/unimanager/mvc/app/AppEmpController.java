package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.service.ExecuteService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class AppEmpController extends ControllerConstants {

    @Autowired
    @Qualifier("appEmpService")
    private ExecuteService appEmpServiceExe;


    @RequestMapping(value = "/appLogin", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appLogin(@RequestParam String mobile, @RequestParam String password){
        if (StringUtil.isNullOrEmpty(mobile)){
            return toJSONString(new ErrorTip(1, "请输入手机号"));
        }
        if (StringUtil.isNullOrEmpty(password)){
            return toJSONString(new ErrorTip(1, "请输入密码"));
        }
        Object[] params = new Object[]{mobile, password};
        try {
            Emp emp = (Emp) appEmpServiceExe.execute(params);
            DataTip tip = new DataTip();
            tip.setData(emp);
            return toJSONString(tip);
        }catch (Exception e){
            String msg = e.getMessage();
            if (msg.equals("NotFound")){
                return toJSONString(new ErrorTip(1, "账号不存在！"));
            }else  if (msg.equals("PassNull")){
                return toJSONString(new ErrorTip(1, "密码不能为空！"));
            }else  if (msg.equals("PassError")){
                return toJSONString(new ErrorTip(1, "密码错误！"));
            } else  if (msg.equals("NotUse")){
                return toJSONString(new ErrorTip(1, "账号被禁用，请联系客服！"));
            } else{
                return toJSONString(new ErrorTip(1, "登录失败，请稍后重试！"));
            }
        }
    }

    @Autowired
    @Qualifier("appEmpService")
    private SaveService appEmpServiceSave;

    @RequestMapping(value = "/appReg", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appReg(Emp emp){
        if(emp == null){
            return toJSONString(new ErrorTip(1, "注册失败！请联系客服"));
        }
        if (StringUtil.isNullOrEmpty(emp.getMobile())){
            return toJSONString(new ErrorTip(1, "请输入手机号"));
        }
        if (StringUtil.isNullOrEmpty(emp.getPassword())){
            return toJSONString(new ErrorTip(1, "请输入密码"));
        }
        try {
            String empid = (String) appEmpServiceSave.save(emp);
            DataTip tip = new DataTip();
            tip.setData(empid);
            return toJSONString(tip);
        }catch (Exception e){
            String msg = e.getMessage();
            if (msg.equals("MobileNotFound")){
                return toJSONString(new ErrorTip(1, "手机号不存在，请联系客服！"));
            }else if (msg.equals("MobileHasExist")){
                return toJSONString(new ErrorTip(1, "该手机号已经注册，请直接登录！"));
            }else{
                return toJSONString(new ErrorTip(1, "注册失败，请稍后重试！"));
            }
        }
    }


    @Autowired
    @Qualifier("appEmpService")
    private UpdateService appEmpServiceUpdate;

    @RequestMapping(value = "/appUpdateCover", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appUpdateCover(Emp emp){
        if(emp == null){
            return toJSONString(new ErrorTip(1, "注册失败！请联系客服"));
        }
        if (StringUtil.isNullOrEmpty(emp.getEmpid())){
            return toJSONString(new ErrorTip(1, "请检查账号"));
        }
        if (StringUtil.isNullOrEmpty(emp.getCover())){
            return toJSONString(new ErrorTip(1, "请检查头像是否正确选择"));
        }
        try {
            appEmpServiceUpdate.update(emp);
            return toJSONString(SUCCESS);
        }catch (Exception e){
            String msg = e.getMessage();
            return toJSONString(new ErrorTip(1, "更新头像失败，请稍后重试！"));
        }
    }


    @Autowired
    @Qualifier("appEmpSecondService")
    private UpdateService appEmpSecondServiceUpdate;

    @RequestMapping(value = "/appUpdateProfile", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appUpdateProfile(Emp emp){
        if(emp == null){
            return toJSONString(new ErrorTip(1, "会员信息不存在，请检查！"));
        }
        try {
            Emp emp1 = (Emp) appEmpSecondServiceUpdate.update(emp);
            DataTip tip = new DataTip();
            tip.setData(emp1);
            return toJSONString(tip);
        }catch (Exception e){
            String msg = e.getMessage();
            if (msg.equals("null")){
                return toJSONString(new ErrorTip(1, "更新资料失败，请检查填写信息是否有误！"));
            }else
            if (msg.equals("empidnull")){
                return toJSONString(new ErrorTip(1, "更新资料失败，请检查用户是否存在！"));
            }else{
                return toJSONString(new ErrorTip(1, "更新资料失败，请稍后重试！"));
            }
        }
    }

}
