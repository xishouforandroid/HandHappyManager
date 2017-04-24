package com.liangxunwang.unimanager.baiduyun.channel.model;


import com.liangxunwang.unimanager.baiduyun.annotation.HttpPathKeyName;
import com.liangxunwang.unimanager.baiduyun.annotation.R;

public class QueryDeviceTypeRequest extends ChannelRequest {

    @HttpPathKeyName(param = R.OPTIONAL)
    private Long channelId = null;

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

}
