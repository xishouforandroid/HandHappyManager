package com.liangxunwang.unimanager.baiduyun.demo;


import com.liangxunwang.unimanager.baiduyun.annotation.JSonPath;

import java.util.LinkedList;
import java.util.List;

public class QueryStatisticMsgResponse extends PushResponse {

	@JSonPath(path="response_params\\result")
	private List<MsgStatInfo> msgStats = new LinkedList<MsgStatInfo>();

	public List<MsgStatInfo> getMsgStats () {
		return msgStats;
	}
}
