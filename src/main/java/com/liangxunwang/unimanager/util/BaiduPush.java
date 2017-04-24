package com.liangxunwang.unimanager.util;


import com.liangxunwang.unimanager.baiduyun.channel.auth.ChannelKeyPair;
import com.liangxunwang.unimanager.baiduyun.demo.*;
import com.liangxunwang.unimanager.baiduyun.exception.PushClientException;
import com.liangxunwang.unimanager.baiduyun.exception.PushServerException;
import com.liangxunwang.unimanager.baiduyun.log.YunLogEvent;
import com.liangxunwang.unimanager.baiduyun.log.YunLogHandler;

/**
 * Created by Administrator on 2016/3/26 0026.
 */
public class BaiduPush {
    //msgtypeid  :1系统通知 2系统资讯 3活动公告  4交往消息
    public static void PushMsgToAll (int deveiceType, String title, String content, String msgtypeid) {
        /*1. 创建PushKeyPair
         *用于app的合法身份认证
         *apikey和secretKey可在应用详情中获取
         */

        PushKeyPair pair = null;
        if (deveiceType == 3) {
            pair = new PushKeyPair(Constants.API_KEY, Constants.SECRET_KEY);
        }else {
            pair = new PushKeyPair(Constants.IOS_API_KEY, Constants.IOS_SECRET_KEY);
        }
        // 2. 创建BaiduPushClient，访问SDK接口
        BaiduPushClient pushClient = new BaiduPushClient(pair,
                BaiduPushConstants.CHANNEL_REST_URL);

        // 3. 注册YunLogHandler，获取本次请求的交互信息
        pushClient.setChannelLogHandler (new YunLogHandler() {
            @Override
            public void onHandle (YunLogEvent event) {
                System.out.println(event.getMessage());
            }
        });

        try {
            // 4. 设置请求参数，创建请求实例
            PushMsgToAllRequest request = new PushMsgToAllRequest().
                    addMsgExpires(new Integer(3600)).   //设置消息的有效时间,单位秒,默认3600*5.
                    addMessageType(1).              //设置消息类型,0表示透传消息,1表示通知,默认为0.
                    addMessage("{\"title\":\"" +title + "\",\"description\":\"" + content + "\",\"custom_content\":{\"msgtypeid\":\"" + msgtypeid+"\"}}").
                    addDeviceType(deveiceType);      //设置设备类型，deviceType => 1 for web, 2 for pc,
            //3 for android, 4 for ios, 5 for wp.
            // 5. 执行Http请求
            PushMsgToAllResponse response = pushClient.
                    pushMsgToAll(request);
            // 6. Http请求返回值解析
            System.out.println("msgId: " + response.getMsgId()
                    + ",sendTime: " + response.getSendTime());
        } catch (PushClientException e) {
            //ERROROPTTYPE 用于设置异常的处理方式 -- 抛出异常和捕获异常,
            //'true' 表示抛出, 'false' 表示捕获。
            if (BaiduPushConstants.ERROROPTTYPE) {
                try {
                    throw e;
                } catch (PushClientException e1) {
                    e1.printStackTrace();
                }
            } else {
                e.printStackTrace();
            }
        } catch (PushServerException e) {
            if (BaiduPushConstants.ERROROPTTYPE) {
                try {
                    throw e;
                } catch (PushServerException e1) {
                    e1.printStackTrace();
                }
            } else {
                System.out.println(String.format(
                        "requestId: %d, errorCode: %d, errorMsg: %s",
                        e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
            }
        }
    }

    public static void PushMsgToSingleDevice (int deveiceType, String title, String content, String msgtypeid ,String channelId) {
          /*1. 创建PushKeyPair
         *用于app的合法身份认证
         *apikey和secretKey可在应用详情中获取
         */
        PushKeyPair pair = null;
        if (deveiceType == 3) {
            pair = new PushKeyPair(Constants.API_KEY, Constants.SECRET_KEY);
        }else {
            pair = new PushKeyPair(Constants.IOS_API_KEY, Constants.IOS_SECRET_KEY);
        }

        // 2. 创建BaiduPushClient，访问SDK接口
        BaiduPushClient pushClient = new BaiduPushClient(pair,
                BaiduPushConstants.CHANNEL_REST_URL);

        // 3. 注册YunLogHandler，获取本次请求的交互信息
        pushClient.setChannelLogHandler (new YunLogHandler() {
            @Override
            public void onHandle (YunLogEvent event) {
                System.out.println(event.getMessage());
            }
        });

        try {
            // 4. 设置请求参数，创建请求实例
            PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest().
                    addChannelId(channelId).
                    addMsgExpires(new Integer(3600)).   //设置消息的有效时间,单位秒,默认3600*5.
                    addMessageType(1).              //设置消息类型,0表示透传消息,1表示通知,默认为0.
                    addMessage("{\"title\":\"" + title + "\",\"description\":\"" + content + "\",\"custom_content\":{\"msgtypeid\":\"" + msgtypeid +"\"}}").
                    addDeviceType(deveiceType);      //设置设备类型，deviceType => 1 for web, 2 for pc,
            //3 for android, 4 for ios, 5 for wp.
            // 5. 执行Http请求
            PushMsgToSingleDeviceResponse response = pushClient.
                    pushMsgToSingleDevice(request);
            // 6. Http请求返回值解析
            System.out.println("msgId: " + response.getMsgId()
                    + ",sendTime: " + response.getSendTime());
        } catch (PushClientException e) {
            //ERROROPTTYPE 用于设置异常的处理方式 -- 抛出异常和捕获异常,
            //'true' 表示抛出, 'false' 表示捕获。
            if (BaiduPushConstants.ERROROPTTYPE) {
                try {
                    throw e;
                } catch (PushClientException e1) {
                    e1.printStackTrace();
                }
            } else {
                e.printStackTrace();
            }
        } catch (PushServerException e) {
            if (BaiduPushConstants.ERROROPTTYPE) {
                try {
                    throw e;
                } catch (PushServerException e1) {
                    e1.printStackTrace();
                }
            } else {
                System.out.println(String.format(
                        "requestId: %d, errorCode: %d, errorMsg: %s",
                        e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
            }
        }

    }
}
