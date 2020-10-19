package com.sflc.websocket.util.message.text.entity;

import com.sflc.websocket.model.Message;
import com.sflc.websocket.util.message.text.Text;

import java.time.format.DateTimeFormatter;

/**
 * @ClassName CommonText
 * @Description 普通消息基础文本类
 * @Author Areogel
 * @Date 2020/9/16 14:48
 * @Version 1.0
 */
public class CommonText implements Text {

    Message message;

    public CommonText() {
    }

    public CommonText(Message message) {
        this.message = message;
    }

    @Override
    public String getHead() {
        return "【" + message.getFrom() + "】";
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
