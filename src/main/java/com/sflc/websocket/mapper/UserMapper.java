package com.sflc.websocket.mapper;

import com.sflc.websocket.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    User getUserByCode(String code);

    User getUserById(String id);
}
