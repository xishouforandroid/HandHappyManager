package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.alipay.AlipayNotify;
import com.liangxunwang.unimanager.model.Order;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.ControllerConstants;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2015/8/17.
 */
@Controller
public class AppPayZfbController extends ControllerConstants {

    /**
     * 微信支付回调-会员认证
     * @return
     */
    @RequestMapping(value = "/payZfbHyNotifyAction",   produces = "text/html;charset=UTF-8", method={RequestMethod.POST})
    @ResponseBody
    public String payZfbHyNotifyAction(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();

        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]:valueStr + values[i] + ",";
            }
//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }

//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
//商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

//支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");


//交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

//异步通知ID
        String notify_id=request.getParameter("notify_id");

//sign
        String sign=request.getParameter("sign");

        if(notify_id!=""&&notify_id!=null){
            if(AlipayNotify.verifyResponse(notify_id).equals("true"))//判断成功之后使用getResponse方法判断是否是支付宝发来的异步通知。
            {
//                if(AlipayNotify.getSignVeryfy(params, sign))//使用支付宝公钥验签
//                {
                    if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
                        synchronized (this){
                            updateOrder(out_trade_no);
                        }
                    }

                    return "success" ;
//                }
//                else//验证签名失败
//                {
//                    return "sign fail" ;
//                }
            }
            else//验证是否来自支付宝的通知失败
            {
                return "response fail" ;
            }
        }
        else{
            return "no notify message" ;
        }
    }

    @Autowired
    @Qualifier("appOrderMakeService")
    private UpdateService appOrderUpdateService;


    void updateOrder(String out_trade_no){
        Order order = new Order();
        order.setOut_trade_no(out_trade_no);
        appOrderUpdateService.update(order);
    }


    /**
     * 微信支付回调-会员认证
     * @return
     */
    @RequestMapping(value = "/payZfbCxNotifyAction",   produces = "text/html;charset=UTF-8", method={RequestMethod.POST})
    @ResponseBody
    public String payZfbCxNotifyAction(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();

        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]:valueStr + values[i] + ",";
            }
//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }

//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
//商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

//支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");


//交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

//异步通知ID
        String notify_id=request.getParameter("notify_id");

//sign
        String sign=request.getParameter("sign");

        if(notify_id!=""&&notify_id!=null){
            if(AlipayNotify.verifyResponse(notify_id).equals("true"))//判断成功之后使用getResponse方法判断是否是支付宝发来的异步通知。
            {
//                if(AlipayNotify.getSignVeryfy(params, sign))//使用支付宝公钥验签
//                {
                    if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
                        synchronized (this){
                            updateOrderCx(out_trade_no);
                        }
                    }

                    return "success" ;
//                }
//                else//验证签名失败
//                {
//                    return "sign fail" ;
//                }
            }
            else//验证是否来自支付宝的通知失败
            {
                return "response fail" ;
            }
        }
        else{
            return "no notify message" ;
        }

    }


    @Autowired
    @Qualifier("orderUpdateWxService")
    private UpdateService orderUpdateWxService;


    void updateOrderCx(String out_trade_no){
        Order order = new Order();
        order.setOut_trade_no(out_trade_no);
        orderUpdateWxService.update(order);
    }



}
