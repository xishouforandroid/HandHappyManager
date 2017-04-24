package com.liangxunwang.unimanager.baiduyun.demo;


import com.liangxunwang.unimanager.baiduyun.annotation.HttpParamKeyName;
import com.liangxunwang.unimanager.baiduyun.annotation.JSonPath;
import com.liangxunwang.unimanager.baiduyun.annotation.R;

public class PushBatchUniMsgResponse extends PushResponse {

	@JSonPath(path="response_params\\msg_id")
    @HttpParamKeyName(name= BaiduPushConstants.MSG_ID, param= R.REQUIRE)
    private String msgId = null;
    
    @JSonPath(path="response_params\\send_time")
    @HttpParamKeyName(name= BaiduPushConstants.SEND_TIME, param= R.REQUIRE)
    private long sendTime;
    
    public String getMsgId () {
    	return msgId;
    }
    public long getSendTime () {
    	return sendTime;
    }
}
