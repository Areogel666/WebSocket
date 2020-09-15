package com.sflc.websocket.service.impl;


import com.sflc.websocket.model.User;
import com.sflc.websocket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sflc.websocket.mapper.UserMapper;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User getUserByCode(String code) {
        User s = userMapper.getUserByCode(code);
        if (s != null)
            return s;
        else
            return new User();
    }

    @Override
    public User getUserbyId(String id) {
        User s = userMapper.getUserById(id);
        if (s != null)
            return s;
        else
            return new User();
    }


}
