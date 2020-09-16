package com.sflc.websocket.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.sflc.websocket.base.MessageType;
import com.sflc.websocket.util.message.TextGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import com.sflc.websocket.model.Message;

@Slf4j
@ServerEndpoint("/webSocket/{userCode}")
@Component
public class WebSocketServer {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineNum = new AtomicInteger();

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。Vector存储多点登录session
    private static ConcurrentHashMap<String, Vector<Session>> sessionPools = new ConcurrentHashMap<>();

    /**
     * 发送消息
     *
     * @param session
     * @param message
     * @description
     * @author Areogel
     * @date 2020/9/15 13:41
     */
    private void sendMessage(Session session, String message) {
        if (session != null) {
            ReentrantLock sessionLock = new ReentrantLock();
            sessionLock.lock();
            try {
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(">>sendMessage>>>>消息发送失败");
            } finally {
                sessionLock.unlock();
            }
            log.debug("发送数据：" + message);
        }
    }

    /**
     * 给指定用户发送信息
     *
     * @param userCode
     * @param message
     * @return boolean
     * @description
     * @author Areogel
     * @date 2020/9/15 14:10
     */
    public boolean sendInfo(String userCode, String message) {
        AtomicBoolean flag = new AtomicBoolean(false);
        Vector<Session> sessionVector = sessionPools.get(userCode);
        Optional.ofNullable(sessionVector).ifPresent(sv -> {
            sv.forEach(session -> sendMessage(session, message));
            flag.set(true);
        });
        return flag.get();
    }

    /**
     * 群发消息
     * @description
     * @param message
	 * @return boolean
     * @author Areogel
     * @date 2020/9/15 15:06
     */
    public boolean broadcast(String message) {
        AtomicBoolean flag = new AtomicBoolean(false);
        sessionPools.values().forEach(sv ->
                sv.forEach(session -> sendMessage(session, message))
        );
        flag.set(true);
        return flag.get();
    }

    /**
     * 建立连接成功调用
     * @description
     * @param session
     * @param userCode
     * @author Areogel
     * @date 2020/9/15 16:42
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userCode") String userCode) {
        //先判断是否已有该用户session
        Vector<Session> sessionVector = sessionPools.get(userCode);
        if (sessionVector != null) {
            sessionVector.add(session);
        } else {
            sessionVector = new Vector<>();
            sessionVector.add(session);
            sessionPools.put(userCode, sessionVector);
            addOnlineCount();
            log.info(userCode + "加入webSocket！当前人数为：" + onlineNum);
            // 广播上线消息
            Message msg = new Message();
            msg.setDate(LocalDateTime.now());
            msg.setFrom(userCode);
            msg.setTo("0");
            msg.setText(TextGenerator.getTextGenerator(msg).loginMessageText());
            broadcast(JSON.toJSONString(msg, true));
        }
    }

    /**
     * 关闭连接时调用
     * @description
     * @param session
     * @param userCode
     * @author Areogel
     * @date 2020/9/15 17:04
     */
    @OnClose
    public void onClose(Session session, @PathParam(value = "userCode") String userCode) {
        Vector<Session> sessionVector = sessionPools.get(userCode);
        if (sessionVector == null) {
            sessionPools.remove(userCode);
            subOnlineCount();
            log.info(userCode + "断开webSocket连接！当前人数为：" + onlineNum);
        } else {
            if (sessionVector.size() > 1) { //多个session只删除当前
                sessionVector.remove(session);
            } else {
                sessionPools.remove(userCode);
                subOnlineCount();
                log.info(userCode + "断开webSocket连接！当前人数为：" + onlineNum);
                // 广播下线消息
                Message msg = new Message();
                msg.setFrom(userCode);
                msg.setDate(LocalDateTime.now());
                msg.setTo("-2");
                msg.setText(TextGenerator.getTextGenerator(msg).loginMessageText());
                broadcast(JSON.toJSONString(msg, true));
            }
        }
    }

    /**
     * 收到客户端信息后，根据接收人的username把消息推下去或者群发
     * @description to=-1群发消息
     * @param message
     * @author Areogel
     * @date 2020/9/15 17:04
     */
    @OnMessage
    public void onMessage(String message) {
        log.debug("WebSocket服务器接收数据：" + message);
        Message msg = JSON.parseObject(message, Message.class);;
        try {
            msg.setDate(LocalDateTime.now());
            if (msg.getTo().equals("-1")) {
                msg.setText(TextGenerator.getTextGenerator(msg).simpleMessageText());
                broadcast(JSON.toJSONString(msg, true));
            } else {
                msg.setmType(MessageType.MESSAGE_TYPE_COMMON);
                msg.setText(TextGenerator.getTextGenerator(msg).simpleMessageText());
                sendInfo(msg.getTo(), JSON.toJSONString(msg, true));
            }
        } catch (Exception e) {
            e.printStackTrace();
            String from = msg.getFrom();
            msg = new Message();
            msg.setFrom(from);
            msg.setDate(LocalDateTime.now());
            msg.setTo(from);
            msg.setText("消息发送失败");
            sendInfo(from, JSON.toJSONString(msg, true));
        }
    }

    /**
     * 错误时调用
     * @description
     * @param session
     * @param throwable
     * @author Areogel
     * @date 2020/9/15 17:05
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("发生错误：" + throwable.getMessage());
        throwable.printStackTrace();
    }

    /**
     * 新增在线人数
     * @description
     * @author Areogel
     * @date 2020/9/15 17:05
     */
    private static void addOnlineCount() {
        onlineNum.incrementAndGet();
    }

    /**
     * 减少在线人数
     * @description
     * @author Areogel
     * @date 2020/9/15 17:06
     */
    private static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }

    /**
     * 获得该webSocket在线人数
     * @description
     * @return int
     * @author Areogel
     * @date 2020/9/15 17:07
     */
    public static int getOnlineNumber() {
        return onlineNum.get();
    }

    /**
     * 获得当前在线用户sessionPool
     * @description
     * @return java.util.concurrent.ConcurrentHashMap<java.lang.String , java.util.Vector < javax.websocket.Session>>
     * @author Areogel
     * @date 2020/9/15 17:40
     */
    public static ConcurrentHashMap<String, Vector<Session>> getSessionPools() {
        return sessionPools;
    }
}
