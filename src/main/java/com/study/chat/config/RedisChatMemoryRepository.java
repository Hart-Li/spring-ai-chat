package com.study.chat.config;

import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.messages.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc 基于 Redis 的存储库（自定义）
 * @date 2025-08-13 18:07
 */
@Component
public class RedisChatMemoryRepository implements ChatMemoryRepository {

    @Resource
    private RedisTemplate<String, SerializableMessage> redisTemplate;

    @Override
    public List<Message> findByConversationId(String conversationId) {
        // 从 Redis 中获取指定会话 ID 的消息列表（0代表首个元素，-1代表列表最后的元素。）
        List<SerializableMessage> serializableMessages = redisTemplate.opsForList().range(conversationId, 0, -1);

        // 处理空结果
        if (serializableMessages.isEmpty()) {
            return List.of();
        }
        // 处理非空结果
        return serializableMessages.stream()
            .map(SerializableMessage::toMessage)
            .toList();
    }

    @Override
    public void saveAll(String conversationId, List<Message> messages) {
        // 删除指定会话ID的历史会话
        redisTemplate.delete(conversationId);

        // 数据转换
        List<SerializableMessage> serializableMessages = messages.stream()
            .map(SerializableMessage::new)
            .toList();
        // 向 Redis 列表的右侧（即尾部）一次性添加多个元素
        if (!serializableMessages.isEmpty()) {
            redisTemplate.opsForList().rightPushAll(conversationId, serializableMessages);
        }
    }

    @Override
    public List<String> findConversationIds() {
        return List.of();
    }

    @Override
    public void deleteByConversationId(String conversationId) {

    }
}
