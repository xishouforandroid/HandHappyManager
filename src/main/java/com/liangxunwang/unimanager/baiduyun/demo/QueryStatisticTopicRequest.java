package com.liangxunwang.unimanager.baiduyun.demo;


import com.liangxunwang.unimanager.baiduyun.annotation.HttpParamKeyName;
import com.liangxunwang.unimanager.baiduyun.annotation.R;
import com.liangxunwang.unimanager.baiduyun.annotation.RangeRestrict;

public class QueryStatisticTopicRequest extends PushRequest {

	@HttpParamKeyName(name= BaiduPushConstants.TOPIC_ID, param= R.REQUIRE)
	@RangeRestrict(minLength=1, maxLength=128)
	private String topicId = null;
	
	// get
	public String getTopicId () {
		return topicId;
	}
	// set
	public void setTopicId (String topicId) {
		this.topicId = topicId;
	}
	// add
	public QueryStatisticTopicRequest addTopicId (String topicId) {
		this.topicId = topicId;
		return this;
	}
    public QueryStatisticTopicRequest addDeviceType (Integer deviceType) {
    	this.deviceType = deviceType;
    	return this;
    }
	public QueryStatisticTopicRequest addExpires(Long requestTimeOut) {
		this.expires = requestTimeOut;
		return this;
	}
}
