package com.sflc.websocket.util.message;

import com.sflc.websocket.base.MessageType;
import com.sflc.websocket.model.Message;
import com.sflc.websocket.util.message.entity.CommonText;
import com.sflc.websocket.util.message.entity.SystemText;
import com.sflc.websocket.util.message.entity.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @ClassName TextGenerator
 * @Description 消息文本生成器
 * @Author Areogel
 * @Date 2020/9/16 14:39
 * @Version 1.0
 */
public class TextGenerator {
    private Map<MessageType, Text> textMap = new HashMap<>();
    private Text text;
    private Message message;
    private static volatile TextGenerator instance;

    private TextGenerator(Message message) {
        textMap.put(MessageType.MESSAGE_TYPE_SYSTEM, new SystemText(message));
        textMap.put(MessageType.MESSAGE_TYPE_LOGIN, new SystemText(message));
        textMap.put(MessageType.MESSAGE_TYPE_COMMON, new CommonText(message));
    }

    /**
     * 获得文本生成器实例
     *
     * @param message
     * @return com.sflc.websocket.util.message.TextGenerator
     * @description 单例模式
     * @author Areogel
     * @date 2020/9/16 16:56
     */
    public static TextGenerator getTextGenerator(Message message) {
        if (instance == null) {
            synchronized (TextGenerator.class) {
                if (instance == null) {
                    instance = new TextGenerator(message);
                }
            }
        }
        MessageType mType = message.getmType();
        instance.text = Optional.ofNullable(instance.textMap.get(mType)).orElseGet(() -> new SystemText(message));
        instance.message = message;
        return instance;
    }

    public String simpleMessageText() {
        String textStr = "<span style=\"color:#964a5a;font-size:14px;font-weight:bold;\">"
                + text.getTime() + " -" + text.getHead() + "：</span></br>"
                + "<div style=\"margin-left:14px;margin-top:10px;\"><span style=\"color:#964a5a;font-size:14px;\">"
                + message.getText() + "</span></div>";
        return textStr;
    }

    public String loginMessageText() {
        String textStr = "<span style=\"color:#964a5a;font-size:14px;font-weight:bold;\">"
                + text.getDate() + " " + text.getTime() + "</span></br>"
                + "<span style=\"color:#964a5a;font-size:14px;\">"
                + message.getFrom() + "</span>";
        String to = message.getTo();
        if (Message.MESSAGE_TO_LOGIN.equals(to)) {
            textStr += "<span style=\"color:#964a5a;font-weight:bold;font-size:15px;\">上线了</span>";
        } else if (Message.MESSAGE_TO_LOGOUT.equals(to)) {
            textStr += "<span style=\"color:#964a5a;font-weight:bold;font-size:15px;\">下线了</span>";
        } else {
            throw new RuntimeException("非指定消息类型[0,-2]");
        }
        textStr += "。";
        return textStr;
    }

    public String contractSignMessageText() {
        String textStr = "<span style=\"color:#964a5a;font-size:14px;font-weight:bold;\">"
                + text.getTime() + " -" + text.getHead() + "：</span></br>"
                + "<div style=\"margin-left:14px;margin-top:10px;\"><span style=\"color:#964a5a;font-size:14px;\">"
                + message.getText() + "</span></div>";
        return textStr;
    }
}
