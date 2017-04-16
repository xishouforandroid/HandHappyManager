package com.liangxunwang.unimanager.model;

/**
 * Created by zhl on 2017/4/16.
 */
public class Report {
    private String reportid;
    private String empid;
    private String nickname;//被举报人昵称
    private String content;
    private String is_read;
    private String dateline;

    private String nicknameJbr;//举报人昵称

    public String getNicknameJbr() {
        return nicknameJbr;
    }

    public void setNicknameJbr(String nicknameJbr) {
        this.nicknameJbr = nicknameJbr;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getReportid() {
        return reportid;
    }

    public void setReportid(String reportid) {
        this.reportid = reportid;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }
}
