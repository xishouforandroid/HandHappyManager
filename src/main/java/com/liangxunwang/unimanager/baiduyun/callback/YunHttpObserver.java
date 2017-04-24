package com.liangxunwang.unimanager.baiduyun.callback;


import com.liangxunwang.unimanager.baiduyun.event.YunHttpEvent;

public interface YunHttpObserver {
	
	public void onHandle(YunHttpEvent event);
	
}
