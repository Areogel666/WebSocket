package com.sflc.websocket.controller;

import com.sflc.websocket.model.Message;
import com.sflc.websocket.service.MessageService;
import com.sflc.websocket.service.UserService;
import com.sflc.websocket.base.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/sendMsgSimple")
    @ResponseBody
    public Reply sendMsgSimple(Message message) {
        boolean flag = false;
        String resMsg = "无在线用户";
        String to = message.getTo();
        if (Message.MESSAGE_TO_BROADCAST.equals(to)) {
            flag = messageService.broadCast(message);
        } else if (Message.MESSAGE_TO_LOGIN.equals(to)) {
            // 登录提醒
        } else if (Message.MESSAGE_TO_LOGOUT.equals(to)) {
            // 登出提醒
        } else {
            flag = messageService.sendOne(message);
        }
        resMsg = flag ? "发送成功" : resMsg;
        return new Reply(flag, resMsg);
    }


//	@RequestMapping("getuid")
//	@ResponseBody
//	public User getuid(@RequestParam("code") String code) {
//		User u = userService.getUserByCode(code);
//		return u;
//	}
}
