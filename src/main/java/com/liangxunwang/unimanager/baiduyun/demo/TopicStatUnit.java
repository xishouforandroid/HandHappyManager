package com.liangxunwang.unimanager.baiduyun.demo;


import com.liangxunwang.unimanager.baiduyun.annotation.JSonPath;

public class TopicStatUnit {

	@JSonPath(path="ack")
	private int ack;
	
	public int getAckNum () {
		return ack;
	}
}
