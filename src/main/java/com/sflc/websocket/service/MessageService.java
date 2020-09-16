package com.sflc.websocket.service;


import com.sflc.websocket.model.Message;

public interface MessageService {

    boolean sendLoginMessage(Message message);

    boolean sendSimpleMessage(Message message);

    boolean sendContractSignMessage(Message message);
}
