package com.liangxunwang.unimanager.baiduyun.demo;


import com.liangxunwang.unimanager.baiduyun.exception.PushClientException;
import com.liangxunwang.unimanager.baiduyun.exception.PushServerException;

import java.util.concurrent.Future;

public interface BaiduPushAsync {

	Future<PushMsgToSingleDeviceResponse> pushMsgToSingleDeviceAsync(
			PushMsgToSingleDeviceRequest request)
			throws PushClientException, PushServerException;

}
