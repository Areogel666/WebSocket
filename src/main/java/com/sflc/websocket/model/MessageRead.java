package com.sflc.websocket.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName MessageRead
 * @Description 消息已读类
 * @Author Areogel
 * @Date 2020/9/17 18:02
 * @Version 1.0
 */
@Data
public class MessageRead {

    //主键id
    private int id;
    //用户code
    private String user_code;
    //T_SYS_WEBNOTICE表ID
    private int notice_id;
    //已读状态 0 未读 1 已读
    private int status;
    //已读时间
    private LocalDateTime read_time;
}
