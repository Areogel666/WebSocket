package com.sflc.websocket.util.message.text.entity;

import com.sflc.websocket.model.Message;
import com.sflc.websocket.util.message.text.Text;

import java.time.format.DateTimeFormatter;

/**
 * @ClassName SystemText
 * @Description 系统消息基础文本类
 * @Author Areogel
 * @Date 2020/9/16 14:45
 * @Version 1.0
 */
public class SystemText implements Text {


    Message message;

    public SystemText() {
    }

    public SystemText(Message message) {
        this.message = message;
    }

    @Override
    public String getHead() {
        return "【系统消息】";
    }

    @Override
    public String getTime() {
        return message.getDate().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    @Override
    public String getDate() {
        return message.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public Text setMessageAndGet(Message message) {
        this.message = message;
        return this;
    }
}
