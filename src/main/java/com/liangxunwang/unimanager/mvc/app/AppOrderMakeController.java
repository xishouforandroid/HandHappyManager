package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.Order;
import com.liangxunwang.unimanager.model.OrderInfoAndSign;
import com.liangxunwang.unimanager.model.WxPayObj;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.ControllerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/8/13.
 */
@Controller
public class AppOrderMakeController extends ControllerConstants {

    @Autowired
    @Qualifier("appOrderMakeService")
    private SaveService appOrderMakeService;

    @Autowired
    @Qualifier("appOrderMakeWxService")
    private SaveService appOrderMakeWxService;

    @Autowired
    @Qualifier("appOrderMakeService")
    private UpdateService appOrderUpdateService;

    /**
     * 订单接收---形成订单
     * @return
     */
    @RequestMapping(value = "/orderSave", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String orderSave(Order order){
        try {
            OrderInfoAndSign orderInfoAndSign = (OrderInfoAndSign) appOrderMakeService.save(order);
            DataTip tip = new DataTip();
            tip.setData(orderInfoAndSign);
            return toJSONString(tip);
        }catch (Exception e){
            if (e.getMessage().equals("ISWRONG")){
                return toJSONString(new ErrorTip(1, "支付宝生成订单失败，请稍后重试！"));
            }
            if (e.getMessage().equals("pay_zfb_error")){
                return toJSONString(new ErrorTip(1, "支付宝生成订单失败，支付宝错误！"));
            }
        }
        return null;
    }


    /**
     * 订单接收---形成订单
     * @param order
     * @return
     */
    @RequestMapping(value = "/orderSaveWx", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String orderSaveWx(Order order){

        if(order == null){
            return toJSONString(new ErrorTip(1, "微信生成订单失败，请稍后重试！"));
        }

        try {
            Object[] strs = (Object[]) appOrderMakeWxService.save(order);
            WxPayObj wxPayObj = new WxPayObj();
            wxPayObj.setXmlStr((String) strs[0]);
            wxPayObj.setOut_trade_no((String) strs[1]);
            DataTip tip = new DataTip();
            tip.setData(wxPayObj);
            return toJSONString(tip);
        }catch (Exception e){
            if (e.getMessage().equals("ISWRONG")){
                return toJSONString(new ErrorTip(1, "微信生成订单失败，请稍后重试！"));
            }
            if (e.getMessage().equals("outOfNum")){
                return toJSONString(new ErrorTip(1, "微信生成订单失败，商品数量不够！"));
            }
        }
        return null;
    }

    /**
     * 订单更新，支付订单成功
     * @param order
     * @return
     */
    @RequestMapping(value = "/orderUpdate", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String orderUpdate(Order order){
        appOrderUpdateService.update(order);
        return toJSONString(SUCCESS);
    }



}
