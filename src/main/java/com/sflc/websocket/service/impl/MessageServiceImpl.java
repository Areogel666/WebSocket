package com.sflc.websocket.service.impl;


import com.alibaba.fastjson.JSON;
import com.sflc.websocket.mapper.MessageMapper;
import com.sflc.websocket.model.Message;
import com.sflc.websocket.base.MessageType;
import com.sflc.websocket.service.MessageService;
import com.sflc.websocket.service.WebSocketServer;
import com.sflc.websocket.util.message.TextGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service("MessageService")
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    WebSocketServer socketServer;

    @Override
    public boolean sendSimpleMessage(Message message) {
        TextGenerator generator = TextGenerator.getTextGenerator(message);
        String text = generator.simpleMessageText();
        message.setText(text);
        if ("0".equals(message.getTo())) { //群发
            sendOne(message);
        } else {
            broadCast(message);
        }
        return false;
    }

    @Override
    public boolean sendLoginMessage(Message message) {
        TextGenerator generator = TextGenerator.getTextGenerator(message);
        String text = generator.loginMessageText();
        message.setText(text);
        if ("0".equals(message.getTo())) { //群发
            sendOne(message);
        } else {
            broadCast(message);
        }
        return false;
    }

    @Override
    public boolean sendContractSignMessage(Message message) {
        TextGenerator generator = TextGenerator.getTextGenerator(message);
        String text = generator.contractSignMessageText();
        message.setText(text);
        if ("0".equals(message.getTo())) { //群发
            sendOne(message);
        } else {
            broadCast(message);
        }
        return false;
    }

    /**
     * 向一个人发送消息
     *
     * @param message
     * @description
     * @author Areogel
     * @date 2020/9/14 19:56
     */
    private boolean sendOne(Message message) {
        return socketServer.sendInfo(message.getTo(), JSON.toJSONString(message, true));
    }

    /**
     * 群发消息
     *
     * @param message
     * @description
     * @author Areogel
     * @date 2020/9/14 19:57
     */
    private boolean broadCast(Message message) {
        return socketServer.broadcast(JSON.toJSONString(message, true));
    }

}
