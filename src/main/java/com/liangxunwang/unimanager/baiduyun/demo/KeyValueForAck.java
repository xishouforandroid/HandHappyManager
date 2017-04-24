package com.liangxunwang.unimanager.baiduyun.demo;


import com.liangxunwang.unimanager.baiduyun.annotation.JSonPath;

public class KeyValueForAck {

	@JSonPath(path="key")
	private String timestamp;
	
	@JSonPath(path="value")
	private int value;
	
	public void setKey (String key) {
		this.timestamp = key;
	}
	
	public void setValue (int value) {
		this.value = value;
	}
	
	public String getKey () {
		return timestamp;
	}
	public int getValue () {
		return value;
	}
}
