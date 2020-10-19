package com.sflc.websocket.controller;

import com.sflc.websocket.util.message.MessageType;
import com.sflc.websocket.model.Message;
import com.sflc.websocket.service.MessageService;
import com.sflc.websocket.service.UserService;
import com.sflc.websocket.base.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @ClassName MessageController
 * @Description 消息提醒类（MessageController）
 * @Author Areogel
 * @Date 2020/9/14 11:00
 * @Version 1.0
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    /**
     * 发送一般消息通用接口
     *
     * @param message
     * @param isCache
     * @return com.sflc.websocket.base.Reply
     * @description 用户编辑发送
     * @author Areogel
     * @date 2020/9/16 13:40
     */
    @RequestMapping("/sendSimpleMessage")
    @ResponseBody
    public Reply sendSimpleMessage(Message message, @RequestParam("is_cache") String isCache) {
        String resMsg = "发送失败";
        message.setIs_cache(Boolean.parseBoolean(isCache));
        message.setmType(MessageType.MESSAGE_TYPE_COMMON);
        message.setDate(LocalDateTime.now());
        boolean flag = messageService.sendSimpleMessage(message);
        resMsg = flag ? "发送成功" : message.isIs_cache() ? resMsg : resMsg + "，对方用户不在线";
        return new Reply(flag, resMsg);
    }

    /**
     * 发送系统消息通用接口
     *
     * @param message
     * @param isCache
     * @return com.sflc.websocket.base.Reply
     * @description 用户编辑发送
     * @author Areogel
     * @date 2020/9/16 13:40
     */
    @RequestMapping("/sendSystemMessage")
    @ResponseBody
    public Reply sendSystemMessage(Message message, @RequestParam("is_cache") String isCache) {
        String resMsg = "发送失败";
        message.setIs_cache(Boolean.parseBoolean(isCache));
        message.setmType(MessageType.MESSAGE_TYPE_SYSTEM);
        message.setDate(LocalDateTime.now());
        boolean flag = messageService.sendSystemMessage(message);
        resMsg = flag ? "发送成功" : message.isIs_cache() ? resMsg : resMsg + "，对方用户不在线";
        return new Reply(flag, resMsg);
    }

    /**
     * 发送登录登出消息通知接口
     *
     * @param message
     * @return com.sflc.websocket.base.Reply
     * @description
     * @author Areogel
     * @date 2020/9/16 13:40
     */
    @RequestMapping("/sendLoginMessage")
    @ResponseBody
    public Reply sendLoginMessage(Message message) {
        String resMsg = "发送失败";
        message.setmType(MessageType.MESSAGE_TYPE_LOGIN);
        message.setDate(LocalDateTime.now());
        boolean flag = messageService.sendLoginMessage(message);
        resMsg = flag ? "发送成功" : message.isIs_cache() ? resMsg : resMsg + "，对方用户不在线";
        return new Reply(flag, resMsg);
    }

    /**
     * 发送电子合同签约消息通知接口
     *
     * @param message
     * @param isCache
     * @return com.sflc.websocket.base.Reply
     * @description 系统编辑发送
     * @author Areogel
     * @date 2020/9/16 13:40
     */
    @RequestMapping("/sendContractSignMessage")
    @ResponseBody
    public Reply sendContractSignMessage(Message message, @RequestParam("is_cache") String isCache) {
        String resMsg = "发送失败";
        message.setIs_cache(Boolean.parseBoolean(isCache));
        message.setmType(MessageType.MESSAGE_TYPE_SYSTEM);
        message.setDate(LocalDateTime.now());
        boolean flag = messageService.sendContractSignMessage(message);
        resMsg = flag ? "发送成功" : message.isIs_cache() ? resMsg : resMsg + "，对方用户不在线";
        return new Reply(flag, resMsg);
    }

}
