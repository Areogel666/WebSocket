package com.sflc.websocket.service.impl;


import com.alibaba.fastjson.JSON;
import com.sflc.websocket.mapper.UserMapper;
import com.sflc.websocket.model.MessageRead;
import com.sflc.websocket.util.message.condition.ConditionHandler;
import com.sflc.websocket.mapper.MessageMapper;
import com.sflc.websocket.model.Message;
import com.sflc.websocket.service.MessageService;
import com.sflc.websocket.service.WebSocketServer;
import com.sflc.websocket.util.message.condition.entity;
import com.sflc.websocket.util.message.text.TextGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service("MessageService")
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    WebSocketServer socketServer;

    @Override
    public boolean sendSimpleMessage(Message message) {
        if (!doCache(message)) { //cache failed
            return false;
        }
        String htmlText = TextGenerator.getTextGenerator().genericMessageText(message);
        message.setText(htmlText);
        return doSend(message);
    }

    @Override
    public boolean sendSystemMessage(Message message) {
        if (!doCache(message)) { //cache failed
            return false;
        }
        String htmlText = TextGenerator.getTextGenerator().genericMessageText(message);
        message.setText(htmlText);
        return doSend(message);
    }

    @Override
    public boolean sendLoginMessage(Message message) {
        if (!doCache(message)) { //cache failed
            return false;
        }
        String htmlText = TextGenerator.getTextGenerator().loginMessageText(message);
        message.setText(htmlText);
        return doSend(message);
    }

    @Override
    public boolean sendContractSignMessage(Message message) {
        if (!doCache(message)) { //cache failed
            return false;
        }
        String htmlText = TextGenerator.getTextGenerator().contractSignMessageText(message);
        message.setText(htmlText);
        return doSend(message);
    }

//    /**
//     * 删除系统信息（超过day天）
//     *
//     * @param day
//     * @return
//     * @description
//     * @author Areogel
//     * @date 2020/10/15 16:27
//     */
//    @Override
//    public void deleteSystemMessageAfterDays(int day) {
//        messageMapper.updateNoticeDelAfterDays(day);
//        messageMapper.deleteNoticeWaitReadAfterDays(day);
//    }

    /**
     * 向单个用户发送消息
     *
     * @param message
     * @description
     * @author Areogel
     * @date 2020/9/14 19:56
     */
    private boolean sendOne(Message message) {
        boolean sendFlag = socketServer.sendInfo(message.getTo(), JSON.toJSONString(message, true));
        if (message.isIs_cache()) { //need cache
            //直接插入为未读，前端调用onMessage时更新为已读
            MessageRead mr = new MessageRead();
            mr.setNotice_id(message.getId());
            mr.setUser_code(message.getTo());
            mr.setStatus(0);
            return messageMapper.mergeWebNoticeWaitRead(mr) > 0;
        }
        return sendFlag;
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
        boolean sendFlag = socketServer.broadcast(JSON.toJSONString(message, true));
        if (message.isIs_cache()) { //need cache
            AtomicInteger insCount = new AtomicInteger(0);
            //get all user and insert wait read
            Set<String> allCodeSet = userMapper.getAllUserCode();
            allCodeSet.forEach(code -> {
                // 直接插入为未读，前端调用onMessage时更新为已读
                MessageRead mr = new MessageRead();
                mr.setNotice_id(message.getId());
                mr.setUser_code(code);
                mr.setStatus(0);
                if (messageMapper.mergeWebNoticeWaitRead(mr) > 0) {
                    insCount.incrementAndGet();
                }
            });
//            sendFlag = (allCodeSet.size() == insCount.get()) ? true : false; 由于异步操作，接收方会提前插入已读数据
            sendFlag = (insCount.get() > 0);
        }
        return sendFlag;
    }

    /**
     * TODO 按条件群发消息
     *
     * @param message
     * @description
     * @author Areogel
     * @date 2020/9/17 16:10
     */
    private boolean conditionBroadCast(Message message) {
        // TODO 获得条件MessageCondition类，根据不同条件判断消息群发对象
        entity.Condition condition = new entity.Condition();
        // 循环发送消息
        Set<String> coditionTo = ConditionHandler.handleCondition(condition);
        for (String to : coditionTo) {
            if (!socketServer.sendInfo(to, JSON.toJSONString(message, true))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 发送消息
     *
     * @param message
     * @return boolean
     * @description
     * @author Areogel
     * @date 2020/9/16 19:54
     */
    private boolean doSend(Message message) {
        boolean flag;
        if (Message.MESSAGE_TO_BROADCAST.equals(message.getTo())) { //群发
            flag = broadCast(message);
        } else if (Message.MESSAGE_TO_CONDITION.equals(message.getTo())) {
            flag = conditionBroadCast(message);
        } else {
            flag = sendOne(message);
        }
        return flag;
    }

    /**
     * 消息数据存储
     *
     * @param message
     * @return boolean
     * @description
     * @author Areogel
     * @date 2020/9/16 20:05
     */
    private boolean doCache(Message message) {
        if (!message.isIs_cache()) { //no cache
            return true; //don't insert data into database
        }
        message.setId(messageMapper.getWebNoticeNextId());
        return messageMapper.insertWebNotice(message) > 0;
    }
}
