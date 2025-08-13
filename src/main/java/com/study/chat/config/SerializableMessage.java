package com.study.chat.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Map;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc TODO
 * @date 2025-08-13 18:14
 */
public class SerializableMessage implements Serializable {
    // 定义序列化版本号，用于版本控制
    private static final long serialVersionUID = 1L;

    // 明确 Message 接口实现类的枚举（三种消息类型：系统消息、用户消息、助手消息）
    public enum MessageType { SYSTEM, USER, ASSISTANT }

    private final MessageType messageType;  // 消息类型
    private final String content; // 消息内容
    private final Map<String, Object> metadata; // 消息元数据

    // 从 Spring AI 的 Message 对象构造 SerializableMessage 对象(保存对话记录时使用)
    public SerializableMessage(Message message) {
        this.messageType = mapMessageType(message);
        this.content = message.getText();
        this.metadata = message.getMetadata();
    }

    // 判断属于哪种具体的实现类类型
    private MessageType mapMessageType(Message message) {
        if (message instanceof SystemMessage) {
            return MessageType.SYSTEM;
        } else if (message instanceof UserMessage) {
            return MessageType.USER;
        } else if (message instanceof AssistantMessage) {
            return MessageType.ASSISTANT;
        } else {
            throw new IllegalArgumentException("Unknown message type: " + message.getClass().getName());
        }
    }

    // 将 SerializableMessage 对象转换回 Spring AI 的 Message 对象(查询对话记录时使用)
    public Message toMessage() {
        switch (messageType) {
            case SYSTEM:
                return new SystemMessage(content);
            case USER:
                return new UserMessage(content);
            case ASSISTANT:
                return new AssistantMessage(content, metadata);
            default:
                throw new IllegalArgumentException("Unknown message type: " + messageType);
        }
    }

    @JsonCreator
    public SerializableMessage(
        @JsonProperty("messageType") MessageType messageType,
        @JsonProperty("content") String content,
        @JsonProperty("metadata") Map<String, Object> metadata
    ) {
        this.messageType = messageType;
        this.content = content;
        this.metadata = metadata;
    }

}
