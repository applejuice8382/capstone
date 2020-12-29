package com.example.capstone2_v1.chat;

import androidx.annotation.NonNull;

public class ChatMsgVO {
    private String userid;
    private String crt_dt;
    private String content1;

    public ChatMsgVO(){

    }

    public ChatMsgVO(String userid, String crt_dt, String content1){
        this.userid = userid;
        this.crt_dt = crt_dt;
        this.content1 = content1;
    }

    public String getUserid(){return userid;}

    public void setUserid(String userid) {this.userid = userid; }

    public String getCrt_dt(){return crt_dt;}

    public void setCrt_dt(String crt_dt) {this.crt_dt = crt_dt; }

    public String getContent1(){return content1;}

    public void setContent1(String content1) {this.content1 = content1; }

    @NonNull
    @Override
    public String toString() {
        return "ChatMsgVo{" +
                "userid=" + userid + '\'' +
                ", crt_dt=" + crt_dt + '\'' +
                ", content1=" +content1 + '\'' +
                '}';
    }
}
