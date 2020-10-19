package com.sflc.websocket.util.message.text;

import com.sflc.websocket.model.Message;
import com.sflc.websocket.util.message.MessageType;
import com.sflc.websocket.util.message.text.entity.CommonText;
import com.sflc.websocket.util.message.text.entity.SystemText;

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
    private static volatile TextGenerator instance;

    private TextGenerator() {
        textMap.put(MessageType.MESSAGE_TYPE_SYSTEM, new SystemText());
        textMap.put(MessageType.MESSAGE_TYPE_LOGIN, new SystemText());
        textMap.put(MessageType.MESSAGE_TYPE_COMMON, new CommonText());
    }

    /**
     * 获得文本生成器实例
     *
     * @return com.sflc.websocket.util.message.TextGenerator
     * @description 单例模式
     * @author Areogel
     * @date 2020/9/16 16:56
     */
    public static TextGenerator getTextGenerator() {
        if (instance == null) {
            synchronized (TextGenerator.class) {
                if (instance == null) {
                    instance = new TextGenerator();
                }
            }
        }
        return instance;
    }

    /**
     * 获得Text类
     *
     * @param message
     * @return com.sflc.websocket.util.message.text.Text
     * @description
     * @author Areogel
     * @date 2020/10/16 18:56
     */
    private Text getText(Message message) {
        MessageType mType = message.getmType();
        return Optional.ofNullable(textMap.get(mType))
                .orElseGet(SystemText::new)
                .setMessageAndGet(message);
    }

    /**
     * 生成通用消息文本
     *
     * @param
     * @return java.lang.String
     * @description
     * @author Areogel
     * @date 2020/10/16 18:56
     */
    public String genericMessageText(Message message) {
        Text text = getText(message);
        String textStr = "<span style=\"color:#964a5a;font-size:14px;font-weight:bold;\">"
                + text.getTime() + " -" + text.getHead() + "：</span></br>"
                + "<div style=\"margin-left:14px;margin-top:10px;\"><span style=\"color:#964a5a;font-size:14px;\">"
                + message.getText() + "</span></div>";
        return textStr;
    }

    /**
     * 生成上下线消息文本
     *
     * @param
     * @return java.lang.String
     * @description
     * @author Areogel
     * @date 2020/10/16 18:56
     */
    public String loginMessageText(Message message) {
        Text text = getText(message);
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

    /**
     * 生成合同提醒消息文本
     *
     * @param
     * @return java.lang.String
     * @description
     * @author Areogel
     * @date 2020/10/16 18:56
     */
    public String contractSignMessageText(Message message) {
        Text text = getText(message);
        String textStr = "<span style=\"color:#964a5a;font-size:14px;font-weight:bold;\">"
                + text.getTime() + " -" + text.getHead() + "：</span></br>"
                + "<div style=\"margin-left:14px;margin-top:10px;\"><span style=\"color:#964a5a;font-size:14px;\">"
                + message.getText() + "</span></div>";
        return textStr;
    }
}
