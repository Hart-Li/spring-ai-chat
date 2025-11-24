package com.study.chat.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc TODO
 * @date 2025-11-24 11:44
 */
@Configuration
public class SpringAIConfig {

    // 创建基于默认的大模型的客户端
    @Bean
    public ChatClient openaiChatClient(ChatClient.Builder builder) {
        return builder.build();
    }

    // 创建特定的 ChatMemory实例
    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
            .chatMemoryRepository(new InMemoryChatMemoryRepository()) // 对话记忆默认使用内存存储库
            .maxMessages(3) // 保留最近的 3 条历史记录
            .build();
    }
}
