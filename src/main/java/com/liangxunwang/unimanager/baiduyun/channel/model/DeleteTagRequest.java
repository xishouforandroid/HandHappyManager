package com.liangxunwang.unimanager.baiduyun.channel.model;


import com.liangxunwang.unimanager.baiduyun.annotation.HttpParamKeyName;
import com.liangxunwang.unimanager.baiduyun.annotation.R;
import com.liangxunwang.unimanager.baiduyun.annotation.RangeRestrict;
import com.liangxunwang.unimanager.baiduyun.channel.constants.BaiduChannelConstants;

public class DeleteTagRequest extends ChannelRequest {

    @HttpParamKeyName(name = BaiduChannelConstants.TAG_NAME, param = R.REQUIRE)
    @RangeRestrict(minLength = 1, maxLength = 128)
    private String tag;

    @HttpParamKeyName(name = BaiduChannelConstants.USER_ID, param = R.OPTIONAL)
    @RangeRestrict(minLength = 1, maxLength = 256)
    private String userId;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
