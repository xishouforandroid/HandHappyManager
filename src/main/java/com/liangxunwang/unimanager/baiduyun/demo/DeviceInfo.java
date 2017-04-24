package com.liangxunwang.unimanager.baiduyun.demo;


import com.liangxunwang.unimanager.baiduyun.annotation.JSonPath;

public class DeviceInfo {

	@JSonPath(path="channel_id")
	private String channelId = null;
	
	@JSonPath(path="result")
	private int result; // 0 -- successfully delete device from tag, 
	                    // 1 -- fail to delete device from tag
	
	// get
	public String getChannelId () {
		return channelId;
	}
	public int getResult () {
		return result;
	}
}
