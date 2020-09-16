package com.sflc.websocket.model;

import java.time.LocalDateTime;

import com.alibaba.fastjson.annotation.JSONField;
import com.sflc.websocket.base.MessageType;


public class Message {

    //广播消息
    public static final String MESSAGE_TO_BROADCAST = "-1";
    //登录消息
    public static final String MESSAGE_TO_LOGIN = "0";
    //登出消息
    public static final String MESSAGE_TO_LOGOUT = "-2";
//    //系统通知
//    public static final int MESSAGE_TYPE_SYSTEM = 0;
//    //上下线通知
//    public static final int MESSAGE_TYPE_LOGIN = 1;
//    //普通消息
//    public static final int MESSAGE_TYPE_COMMON = 2;

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
    //消息类型
    public MessageType mType;

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

    public MessageType getmType() {
        return mType;
    }

    public void setmType(MessageType mType) {
        this.mType = mType;
    }

    @Override
    public String toString() {
        return "Message{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", is_cache=" + is_cache +
                ", mType=" + mType +
                '}';
    }
}
