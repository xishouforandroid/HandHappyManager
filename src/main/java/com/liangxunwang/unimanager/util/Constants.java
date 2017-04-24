package com.liangxunwang.unimanager.util;

/**
 * Created by zhl on 2015/1/29.
 */
public class Constants {
        public static final String URL = "http://157j1274e3.iask.in/";
//        public static final String URL = "http://192.168.1.114:8080/";
//        public static final String URL = "http://www.zhlmlt.cn/";

        public static final String QINIU_URL = "http://oo4c4r583.bkt.clouddn.com/";

        public static final String QINIU_SPACE = "meetlove-pic";
        public static final String COVER_DEFAULT = "default_avatar.png";

        public static final String API_KEY = "pO8Xhn7xPciq4I4qOtwr8GiG";
        public static final String SECRET_KEY = "rMoIeSB2RjO2YOVswzx3KxmVhs8kh6E0";

        public static final String IOS_API_KEY = "W38xecAXVYDY955Shv0GGc8f";
        public static final String IOS_SECRET_KEY = "ZrhZicrOHvexKhRoKWFIdyaEsCTrUeom";

        public static final int IOS_TYPE = 1;

        public static final String SAVE_ERROR = "save_error";
        public static final String DEFAULT_SERVICE_TOP_BG = "img/user_bg.jpg";//默认背景图--顶部的
        public static final String DEFAULT_DOWNLOAD_URL = "html/download.html";//默认下载地址

        public static final String HAS_ZAN = "has_zan";

        public static final String HAS_CODE = "has_code";

        public static final String NO_SEND_CODE = "no_send_code";

        public static final String SEND_SMS_ERROR = "send_sms_error";

        public static final String HAS_EXISTS = "has_exists";

        public static final String TOO_MANY_CODE = "too_many_code";

        public static final String CODE_NOT_EQUAL = "code_not_equal";

        public static final String PHONE_ERROR = "phone_error";

        public static final String HX_ERROR = "hx_error";

        public static final String SMS_MESSAGE_URL = "http://60.209.7.78:8080/smsServer/submit";

        public static final Long DAY_MILLISECOND = 86400000L;


        //微信支付回调--会员认证
        public static final String WEIXIN_NOTIFY_URL_HY = URL + "payWxHyNotifyAction.do";
        //微信支付回调--诚信认证
        public static final String WEIXIN_NOTIFY_URL_CX = URL + "payWxCxNotifyAction.do";


        //微信支付回调--会员认证
        public static final String ZFB_NOTIFY_URL_HY = URL + "payZfbHyNotifyAction.do";
        //微信支付回调--诚信认证
        public static final String ZFB_NOTIFY_URL_CX = URL + "payZfbCxNotifyAction.do";


        //appid
        public static final String WX_APP_ID = "wx6ce53935b8d58010";//yum
        //商户号
        public static final String WX_MCH_ID = "1423178402";//yum
        //  API密钥，在商户平台设置
        public static final  String WX_API_KEY="85977cd5719d1aebb5dd6ccaa4fe5bfe";//yum

        public static final  String WX_APP_SECRET="611fcae2cb0a43381be9ee527de1c406";//yum
}
