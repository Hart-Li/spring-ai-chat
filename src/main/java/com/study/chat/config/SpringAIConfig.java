package com.study.chat.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc TODO
 * @date 2025-08-08 14:17
 */
@Configuration
public class SpringAIConfig {

    @Bean
    public ChatClient openAiChatClient(ChatClient.Builder builder) {
        return builder.build();
    }

    // 创建特定的 ChatMemory实例
    /*@Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
            .chatMemoryRepository(new InMemoryChatMemoryRepository())  // 对话记忆默认使用内存存储库
            .maxMessages(3)  // 保留最近的 3 条历史记录
            .build();
    }*/

    // 创建基于 JDBC 存储对话记忆的 ChatMemory 实例
    @Bean
    public ChatMemory chatMemory(JdbcChatMemoryRepository jdbcChatMemoryRepository) {
        return MessageWindowChatMemory.builder()
            .chatMemoryRepository(jdbcChatMemoryRepository)  // 对话记忆使用基于 JDBC 的存储库
            .maxMessages(10)  // 保留最近的 10 条历史记录
            .build();
    }
}
