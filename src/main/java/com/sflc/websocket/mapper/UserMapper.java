package com.sflc.websocket.mapper;

import com.sflc.websocket.model.User;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserMapper {

    User getUserByCode(String code);

    User getUserById(String id);

    Set<String> getAllUserCode();
}
