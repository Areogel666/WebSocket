package com.sflc.websocket.model;

import java.time.LocalDateTime;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;


public class Message {

    //登录上线广播
    public static final String MESSAGE_TO_LOGIN = "0";
    //消息广播
    public static final String MESSAGE_TO_BROADCAST = "-1";
    //下线广播
    public static final String MESSAGE_TO_LOGOUT = "-2";

    //发送者name
    private String from;
    //接收者name
    private String to;
    //发送的文本
    private String text;
    //发送时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    //是否存储
    private boolean is_cache;

    public Message() {
        super();
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isIs_cache() {
        return is_cache;
    }

    public void setIs_cache(boolean is_cache) {
        this.is_cache = is_cache;
    }
}
