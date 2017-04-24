package com.liangxunwang.unimanager.baiduyun.demo;


import com.liangxunwang.unimanager.baiduyun.annotation.JSonPath;

public class CreateTagResponse extends PushResponse {

	@JSonPath(path="response_params\\tag")
	private String tagName = null;
	
	@JSonPath(path="response_params\\result")
	private int result;     // 0 -- tag successfully build, 1 -- fail to build tag
	
	// get
	public String getTagName () {
		return tagName;
	}
	public int getResult () {
		return result;
	}
}
