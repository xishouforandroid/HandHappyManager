package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.AppOrderMakeDao;
import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.dao.HyrzDao;
import com.liangxunwang.unimanager.dao.MessagesDao;
import com.liangxunwang.unimanager.model.*;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.*;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/8/14.
 */
@Service("appOrderMakeService")
public class AppOrderMakeService implements SaveService,UpdateService,FindService {
    @Autowired
    @Qualifier("appOrderMakeDao")
    private AppOrderMakeDao appOrderMakeSaveDao;

    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;

    //保存订单
    @Override
    public Object save(Object object) throws ServiceException {
        Order order = (Order) object;

        //商品总额，用于插入订单金额
        String out_trade_no = UUIDFactory.random();//订单总金额的id
        ShoppingTrade shoppingTrade = new ShoppingTrade();
        shoppingTrade.setOut_trade_no(out_trade_no);
        shoppingTrade.setPay_status("0");
        shoppingTrade.setDateline(System.currentTimeMillis() + "");
        shoppingTrade.setTrade_prices(order.getPayable_amount());

        //保存总订单--和支付宝一致
        appOrderMakeSaveDao.saveTrade(shoppingTrade);

        order.setOrder_no(UUIDFactory.random());
        order.setCreate_time(System.currentTimeMillis() + "");
        order.setOut_trade_no(out_trade_no);

        //保存订单
        appOrderMakeSaveDao.saveList(order);

        String notify_url = "";
        if("0".equals(order.getIs_dxk_order())){
//                private String is_dxk_order;//0认证服务费  1诚信保证金
            notify_url =  Constants.ZFB_NOTIFY_URL_HY;
        }else{
            notify_url =  Constants.ZFB_NOTIFY_URL_CX;
        }

        String orderInfo = StringUtil.getOrderInfo(out_trade_no, "meetLove", "meetLove_pay", order.getPayable_amount(), notify_url);
        // 对订单做RSA 签名
        String sign = StringUtil.sign(orderInfo);

        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
            return new OrderInfoAndSign(orderInfo, sign, out_trade_no);
        } catch (UnsupportedEncodingException e) {
            throw new ServiceException("ISWRONG");
        }
    }


    @Autowired
    @Qualifier("hyrzDao")
    private HyrzDao hyrzDao;

    @Autowired
    @Qualifier("messagesDao")
    private MessagesDao messagesDao;

    //更新订单状态
    @Override
    public Object update(Object object) {
        if (object instanceof Order){
            //跟新主订单和分订单状态
            Order order = (Order) object;
            List<Order> orders = appOrderMakeSaveDao.findOrderByTradeNo(order.getOut_trade_no());
            if(orders != null && orders.size() > 0){
                Order order1 = orders.get(0);
                if(order1 != null){
                    if(!"2".equals(order1.getStatus())){
                        //更新主订单
                        appOrderMakeSaveDao.updateTradeById(order.getOut_trade_no());
                        order.setPay_time(System.currentTimeMillis() + "");
                        //更新分订单
                        appOrderMakeSaveDao.updateOrderById(order);
                        //更新会员到期日期和会员认证状态
                        empDao.updateRzstate2(order1.getEmpid(), "1");

                        Map<String, Object> maphyrz = new HashMap<>();
                        maphyrz.put("empid", order1.getEmpid());
                        List<HappyHandHyrz> lists = hyrzDao.lists(maphyrz);
                        if(lists != null && lists.size()>0)
                        {
                            //说明数据库有认证的数据  更新
                            HappyHandHyrz happyHandHyrz =lists.get(0);
                            String endDate = DateUtil.getCurrentDateTime();
                            String year = endDate.substring(0,4);
                            int yearInt = Integer.parseInt(year)+1;//获得加一年的年份
                            String endtime = endDate.replace(year, String.valueOf(yearInt));
                            happyHandHyrz.setEndtime(DateUtil.getMs(endtime, "yyyy-MM-dd HH:mm:ss") + "");
                            happyHandHyrz.setIs_use("1");
                            hyrzDao.update(happyHandHyrz);
                        }else{
                            //添加会员认证数据
                            HappyHandHyrz happyHandHyrz = new HappyHandHyrz();
                            String endDate = DateUtil.getCurrentDateTime();
                            String year = endDate.substring(0,4);
                            int yearInt = Integer.parseInt(year)+1;//获得加一年的年份
                            String endtime = endDate.replace(year, String.valueOf(yearInt));
                            happyHandHyrz.setEndtime(DateUtil.getMs(endtime, "yyyy-MM-dd HH:mm:ss") + "");
                            happyHandHyrz.setIs_use("1");
                            happyHandHyrz.setStarttime(System.currentTimeMillis() + "");
                            happyHandHyrz.setEmpid(order1.getEmpid());
                            happyHandHyrz.setHyrzid(UUIDFactory.random());
                            hyrzDao.save(happyHandHyrz);
                        }
                        //会员认证成功之后，发送系统消息
                        //todo
                        Emp emp = empDao.findById(order1.getEmpid());
                        if(emp != null){
                            HappyHandMessage happyHandMessage = new HappyHandMessage();
                            happyHandMessage.setMsgid(UUIDFactory.random());
                            happyHandMessage.setDateline(System.currentTimeMillis() + "");
                            happyHandMessage.setTitle("恭喜你成为认证会员，快去寻找幸福吧!");
                            happyHandMessage.setEmpid(order1.getEmpid());
                            messagesDao.save(happyHandMessage);

                            if(!StringUtil.isNullOrEmpty(emp.getChannelId())){
                                BaiduPush.PushMsgToSingleDevice(Integer.parseInt(emp.getDeviceType()), "系统消息", "恭喜你成为认证会员，快去寻找幸福吧!", "1", emp.getChannelId());
                            }
                        }
                    }
                }
            }

        }
        return null;
    }

//
//    private void pushMsg(final String pushId, final String type, final String content){
//        CommonUtil.getThreadPool().execute(new Runnable() {
//            @Override
//            public void run() {
//                ChannelKeyPair pair = null;
//                if (type.equals("3")) {
//                    pair = new ChannelKeyPair(Constants.API_KEY, Constants.SECRET_KEY);
//                } else {
//                    pair = new ChannelKeyPair(Constants.IOS_API_KEY, Constants.IOS_SECRET_KEY);
//                }
//
//                // 2. 创建BaiduChannelClient对象实例
//                BaiduChannelClient channelClient = new BaiduChannelClient(pair);
//                // 3. 若要了解交互细节，请注册YunLogHandler类
//                channelClient.setChannelLogHandler(new YunLogHandler() {
//                    @Override
//                    public void onHandle(YunLogEvent event) {
////                        System.out.println(event.getMessage());
//                    }
//                });
//                try {
//                    // 4. 创建请求类对象
//                    // 手机端的ChannelId， 手机端的UserId， 先用1111111111111代替，用户需替换为自己的
//                    PushUnicastMessageRequest request = new PushUnicastMessageRequest();
//                    request.setDeviceType(Integer.parseInt(type));
//                    if (type.equals("4")) {//如果是苹果手机端要设置这个
//                        request.setDeployStatus(Constants.IOS_TYPE);
//                    }
////            request.setDeviceType(3); // device_type => 1: web 2: pc 3:android  4:ios 5:wp
////            request.setChannelId(Long.parseLong(pushId));
////            request.setChannelId(5100663888284228047L);
//                    request.setUserId(pushId);
//
//                    request.setMessageType(1);
//                    request.setMessage("{\"title\":\"提醒\",\"description\":\"" + content + "\",\"custom_content\": {\"type\":\"2\"}}");
//
////                    logger.info("开始调用百度推送接口");
//                    // 5. 调用pushMessage接口
//                    PushUnicastMessageResponse response = channelClient.pushUnicastMessage(request);
//
////                    logger.info("推送成功----"+response.getSuccessAmount());
//                    // 6. 认证推送成功
////                    System.out.println("push amount : " + response.getSuccessAmount());
//
//                } catch (ChannelClientException e) {
//                    // 处理客户端错误异常
//                    e.printStackTrace();
////                    logger.info("处理客户端异常"+e.getMessage());
//                } catch (ChannelServerException e) {
//                    // 处理服务端错误异常
////                    System.out.println(String.format(
////                            "request_id: %d, error_code: %d, error_message: %s",
////                            e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
//                }
//            }
//        });
//
//    }

    @Override
    public Object findById(Object object) throws ServiceException {
        String order_no = (String) object;
        Order record = appOrderMakeSaveDao.findOrderByOrderNo(order_no);

        if(!StringUtil.isNullOrEmpty(record.getCreate_time())){
            record.setCreate_time(RelativeDateFormat.format(Long.parseLong(record.getCreate_time())));
        }
        if(!StringUtil.isNullOrEmpty(record.getPay_time())){
            record.setPay_time(RelativeDateFormat.format(Long.parseLong(record.getPay_time())));
        }
        return record;
    }
}
