package com.liangxunwang.unimanager.baiduyun.demo;


import com.liangxunwang.unimanager.baiduyun.annotation.JSonPath;

public class QueryDeviceNumInTagResponse extends PushResponse {

	@JSonPath(path="response_params\\device_num")
	private int deviceNum;
	
	// get
	public int getDeviceNum () {
		return deviceNum;
	}
}
