package com.sflc.websocket.base;

import lombok.Data;
import lombok.Getter;

/**
 * @ClassName Reply
 * @Description
 * @Author Areogel
 * @Date 2020/9/14 19:02
 * @Version 1.0
 */
@Getter
public class Reply {

    private boolean flag = true;
    private String msg = "";
    private Object data;

    public Reply(Object data) {
        this.data = data;
    }

    public Reply(boolean flag, String msg) {
        this.flag = flag;
        this.msg = msg;
    }

    public Reply(boolean flag, Object data) {
        this.flag = flag;
        this.data = data;
    }
}
