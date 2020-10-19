package com.sflc.websocket.util.message.text;

import com.sflc.websocket.model.Message;

/**
 * @ClassName Text
 * @Description 消息文本内容接口
 * @Author Areogel
 * @Date 2020/9/16 14:40
 * @Version 1.0
 */
public interface Text {

    public String getHead();

    public String getTime();

    public String getDate();

    public Text setMessageAndGet(Message message);
}
