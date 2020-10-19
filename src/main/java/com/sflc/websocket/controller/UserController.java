package com.sflc.websocket.controller;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.sflc.websocket.service.UserService;
import com.sflc.websocket.service.WebSocketServer;
import io.github.yedaxia.apidocs.ApiDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sflc.websocket.model.User;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


/**
 * @ClassName UserController
 * @Description 用户信息类（UserController）
 * @Author Areogel
 * @Date 2020/9/14 11:00
 * @Version 1.0
 */
@Controller
public class UserController {
    @Autowired
    UserService loginservice;

    /**
     * 登录消息平台
     *
     * @param code        用户账号
     * @param pwd         用户密码
     * @param httpSession session
     * @return java.lang.String
     * @description 账号密码校验
     * @author Areogel
     * @date 2020/9/14 13:13
     */
    @RequestMapping("/loginvalidate")
    public String loginvalidate(@RequestParam("code") String code, @RequestParam("password") String pwd, HttpSession httpSession) {
        if (code == null) {
            return "login";
        }
        User user = loginservice.getUserByCode(code);
        String realpwd = user.getPwd_src();
        if (realpwd != null && pwd.equals(realpwd)) {
            String uid = user.getUid();
            httpSession.setAttribute("uid", uid);
            return "chatroom";
        } else
            return "fail";
    }

    /**
     * 登录页跳转
     *
     * @return java.lang.String
     * @description
     * @author Areogel
     * @date 2020/9/14 13:15
     */
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 登出页跳转
     *
     * @param httpSession session
     * @return java.lang.String
     * @description
     * @author Areogel
     * @date 2020/9/14 13:16
     */
    @RequestMapping("/logout")
    public String logout(HttpSession httpSession) {
        return "login";
    }

    /**
     * 查询当前在线用户
     *
     * @param httpSession session
     * @return com.sflc.websocket.model.User
     * @description
     * @author Areogel
     * @date 2020/9/14 13:16
     */
    @RequestMapping(value = "/currentuser", method = RequestMethod.GET)
    @ResponseBody
    public User currentuser(HttpSession httpSession) {
        String uid = (String) httpSession.getAttribute("uid");
        return loginservice.getUserbyId(uid);
    }

    /**
     * 在线用户（除本账号）
     *
     * @param currentuser 当前用户
     * @return java.util.Set<java.lang.String>
     * @description 用于消息群发
     * @author Areogel
     * @date: 2020/9/14 11:35
     */
    @ApiDoc(stringResult = "{nameset: 'Set<String> 用户账号集合'}")
    @RequestMapping("/onlineusers")
    @ResponseBody
    public Set<String> onlineusers(@RequestParam("currentuser") String currentuser) {
        ConcurrentHashMap<String, Vector<Session>> map = WebSocketServer.getSessionPools();
        Set<String> codeSet = map.keySet();
        return codeSet.stream().filter(code -> !code.equals(currentuser))
                    .collect(Collectors.toSet());

//        Iterator<String> it = codeSet.iterator();
//        Set<String> nameset = new HashSet<String>();
//        while (it.hasNext()) {
//            String entry = it.next();
//            if (!entry.equals(currentuser))
//                nameset.add(entry);
//        }
//        return nameset;
    }
}
