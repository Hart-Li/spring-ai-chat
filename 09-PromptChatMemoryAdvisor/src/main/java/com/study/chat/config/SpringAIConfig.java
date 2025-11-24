package com.study.chat.config;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
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

    @Resource
    private RedisChatMemoryRepository redisChatMemoryRepository;

    // 创建基于默认的大模型的客户端
    @Bean
    public ChatClient openaiChatClient(ChatClient.Builder builder) {
        return builder
            .defaultAdvisors(
                PromptChatMemoryAdvisor.builder(chatMemory(redisChatMemoryRepository)).build())
            .defaultSystem(
                system -> system.text("你是一名{role}，擅长精准而简洁得回答问题")
                    .param("role", "订购助手")).build();
    }

    // 创建特定的 ChatMemory实例
    @Bean
    public ChatMemory chatMemory(RedisChatMemoryRepository redisChatMemoryRepository) {
        return MessageWindowChatMemory.builder()
            .chatMemoryRepository(redisChatMemoryRepository) // 对话记忆使用基于 Redis 的存储库
            .maxMessages(10) // 保留最近的 10 条历史记录
            .build();
    }
}
