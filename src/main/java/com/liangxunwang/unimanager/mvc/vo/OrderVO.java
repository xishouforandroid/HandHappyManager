package com.liangxunwang.unimanager.mvc.vo;

import com.liangxunwang.unimanager.model.Order;

/**
 * Created by zhl on 2017/4/18.
 */
public class OrderVO extends Order {
    private String nickname;
    private String mobile;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
