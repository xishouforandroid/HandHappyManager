package com.liangxunwang.unimanager.baiduyun.demo;


import com.liangxunwang.unimanager.baiduyun.annotation.HttpParamKeyName;
import com.liangxunwang.unimanager.baiduyun.annotation.R;
import com.liangxunwang.unimanager.baiduyun.annotation.RangeRestrict;
import net.sf.json.JSONArray;

public class AddDevicesToTagRequest extends PushRequest {

	@HttpParamKeyName(name= BaiduPushConstants.TAG_NAME, param= R.REQUIRE)
	@RangeRestrict(minLength=1, maxLength=128)
	private String tagName = null;
	
    @HttpParamKeyName(name= BaiduPushConstants.CHANNEL_IDS, param= R.REQUIRE)
    private String channelIds = null;
    
    // get
    public String getTagName () {
    	return tagName;
    }
    public String getChannelIdsString () {
    	return channelIds;
    }
    public String[] getChannelIdsArray () {
    	JSONArray jsonChannelIds = JSONArray.fromObject(channelIds);
    	return (String[]) jsonChannelIds.toArray();
    }
    // set
    public void setTagName (String tagName) {
    	this.tagName = tagName;
    }
    public void setChannelIds (String[] channelIds) {
    	JSONArray tmpChannelIds = new JSONArray();
    	for (int i = 0; i < channelIds.length; i++){
    		tmpChannelIds.add(i, channelIds[i]);
    	}
    	this.channelIds = tmpChannelIds.toString();
    }
    // add
    public AddDevicesToTagRequest addTagName (String tagName) {
    	this.tagName = tagName;
    	return this;
    }
    public AddDevicesToTagRequest addChannelIds (String[] channelIds) {
    	JSONArray tmpChannelIds = new JSONArray();
    	for (int i = 0; i < channelIds.length; i++){
    		tmpChannelIds.add(i, channelIds[i]);
    	}
    	this.channelIds = tmpChannelIds.toString();
    	return this;
    }
    public AddDevicesToTagRequest addDeviceType (Integer deviceType) {
    	this.deviceType = deviceType;
    	return this;
    }
	public AddDevicesToTagRequest addExpires(Long requestTimeOut) {
    	this.expires = requestTimeOut;
		return this;
	}
}
