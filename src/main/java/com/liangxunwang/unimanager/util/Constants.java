package com.liangxunwang.unimanager.util;

/**
 * Created by zhl on 2015/1/29.
 */
public class Constants {
//        public static final String URL = "http://157j1274e3.iask.in/";
        public static final String URL = "http://192.168.1.114:8080/";
//        public static final String URL = "http://www.zhlmlt.cn/";

        public static final String QINIU_URL = "http://oo4c4r583.bkt.clouddn.com/";

        public static final String QINIU_SPACE = "meetlove-pic";
        public static final String COVER_DEFAULT = "default_avatar.png";

        public static final String API_KEY = "HGGcyEBPPlfjIieCVnWSlMD6";
        public static final String SECRET_KEY = "vFypyImE4aFxWspAHTCbpsm4Nh2P2pPN";
        public static final String IOS_API_KEY = "W38xecAXVYDY955Shv0GGc8f";
        public static final String IOS_SECRET_KEY = "ZrhZicrOHvexKhRoKWFIdyaEsCTrUeom";

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

        //商户PID
        public static final String PARTNER = "2088421398530212";
        //商户收款账号
        public static final String SELLER = "shandongyoubang@126.com";
        //商户私钥，pkcs8格式
        public static final String RSA_PRIVATE = "\n" +
                "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALfaqiOS8fV3U8l3" +
                "HX3cOy3cRjuLFpIMBQOcNeF3XmJ75XDBXajdKdxYTJ/23j7UKBUTeJyUMgFdehwK" +
                "PyyuDCOfggOszqdd8LC15nj+yXzatwXQ8E3aA77NWaNxGKw23JN7Q8XVIhXWuHKi" +
                "guTM4HdsJ3RSHhSfaQpbZQVxVpgBAgMBAAECgYAFp9RUA9Etm94X9HITzRKE9qGy" +
                "czTeiUAePE/r/6AHp4gHT+txplwDSzQQN6fehug0vcSYDHb9U8LN2gLXfQMih/9g" +
                "R714MgY0CYocfGw/JQkqa60YsAzVMfqFUQv+QNxe5CYMHSz62YVbzqFE5/4+u2dc" +
                "kzwgsnwS+Wtjk+OAAQJBAOzvjqtqe7ju5WDJZCiCc95tlBfY9Ve8nIqP8m5V5rf4" +
                "h54SyV2zWhI9ieUUixZyBTwMVFfZ+aVMwyEFwMGJzaECQQDGpbkuYB18k09tusrd" +
                "S6lUoODarSQLvE8cOILX7YbpOWlCJstLnHHMlKUvlIEr316tTOfGXgTN0nEyc/eS" +
                "dO5hAkEAq/GNT8iO0L5Np9d+4AcLnzdWaZgBNBKkZ8ne3UNpk8jAYm6vw1gM3X/b" +
                "OmuWJEvKkLFB5B9DlDoIwUYzhEBFAQJAeyohiQl6/tRvOP/J7C+PS7N79tnHMaeN" +
                "/VdkePEC5vP7uVJIVhcnz8qSKNAQztzt3NGEG1AL9nl2SevxIlNxwQJBAN6kNRO9" +
                "uoD6yZjb7CSVyrV7Os/V6I792jI6vHGwuFifSHGiRL7JWGdloH2N7iQhm0nPliSD" +
                "iqfFZ83jNeETt7w=";
        //支付宝公钥
        public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC32qojkvH1d1PJdx193Dst3EY7ixaSDAUDnDXhd15ie+VwwV2o3SncWEyf9t4+1CgVE3iclDIBXXocCj8srgwjn4IDrM6nXfCwteZ4/sl82rcF0PBN2gO+zVmjcRisNtyTe0PF1SIV1rhyooLkzOB3bCd0Uh4Un2kKW2UFcVaYAQIDAQAB";


        //微信统一下单notify_url
        public static final String WEIXIN_NOTIFY_URL = URL + "orderSaveWxFk.do";

        //appid
        public static final String WX_APP_ID = "wxe48c235e104c5332";//yum
        //商户号
        public static final String WX_MCH_ID = "1368485802";//yum
        //  API密钥，在商户平台设置
        public static final  String WX_API_KEY="cd46a2cae4981a4fab91b2c3271052aa";//yum
}
