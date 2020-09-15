package com.sflc.websocket.service;


import com.sflc.websocket.model.Message;

public interface MessageService {


    boolean sendOne(Message message);

    boolean broadCast(Message message);
}
