package com.sflc.websocket.mapper;

import com.sflc.websocket.model.Message;
import com.sflc.websocket.model.MessageRead;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageMapper {

    int insertWebNotice(Message message);

    int insertWebNoticeWaitRead(MessageRead mr);

    int mergeWebNoticeWaitRead(MessageRead mr);

    int getWebNoticeNextId();

//    int updateNoticeDelAfterDays(int day);

//    int deleteNoticeWaitReadAfterDays(int day);
}
