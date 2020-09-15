package com.sflc.websocket.service;


import com.sflc.websocket.model.User;

public interface UserService {

    User getUserByCode(String code);

    User getUserbyId(String id);
}
