package com.sflc.websocket.service.impl;


import com.alibaba.fastjson.JSON;
import com.sflc.websocket.mapper.MessageMapper;
import com.sflc.websocket.mapper.UserMapper;
import com.sflc.websocket.model.Message;
import com.sflc.websocket.model.User;
import com.sflc.websocket.service.MessageService;
import com.sflc.websocket.service.UserService;
import com.sflc.websocket.service.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

@Service("MessageService")
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    WebSocketServer socketServer;

    /**
     * 向一个人发送消息
     * @description
     * @param message
     * @author Areogel
     * @date 2020/9/14 19:56
     */
    @Override
    public boolean sendOne(Message message) {
//        ConcurrentHashMap<String, Vector<Session>> sessionMap = WebSocketServer.getSessionPools();
//        if (sessionMap != null) {
//            sessionMap.entrySet().forEach((entry) -> {
//                if (entry.getKey().equals(message.getTo())) {
//                    entry.getValue().forEach((session) -> {
//                        try {
//                            message.setDate(LocalDateTime.now());
//                            session.getBasicRemote().sendText(JSON.toJSONString(message, true));
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                            throw new RuntimeException(">>sendOne>>>>消息发送失败");
//                        }
//                    });
//                }
//            });
//            return true;
//        }
//        return false;
        return socketServer.sendInfo(message.getTo(), JSON.toJSONString(message, true));
    }

    /**
     * 群发消息
     * @description
     * @param message
     * @author Areogel
     * @date 2020/9/14 19:57
     */
    @Override
    public boolean broadCast(Message message) {
        return socketServer.broadcast(JSON.toJSONString(message, true));
    }
}
