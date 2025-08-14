package com.study.chat.config;

import jakarta.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
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

    @Resource
    private RedisChatMemoryRepository redisChatMemoryRepository;

    @Bean
    public ChatClient openAiChatClient(ChatClient.Builder builder) {
        // 敏感词
        List<String> sensitiveWords = Arrays.asList("色情", "暴力", "有颜色的");
        // 响应信息
        String failureResponse = "无法回答此问题，请编辑后重试~";
        SafeGuardAdvisor safeGuardAdvisor = SafeGuardAdvisor.builder()
            .sensitiveWords(sensitiveWords)
            .failureResponse(failureResponse)
            .order(0)
            .build();
        return builder
            .defaultAdvisors(safeGuardAdvisor)
            .defaultSystem(
                system -> system.text("你是一名{role}，擅长精准而简洁得回答问题")
                    .param("role", "订购助手")).build();
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
    /*@Bean
    public ChatMemory chatMemory(JdbcChatMemoryRepository jdbcChatMemoryRepository) {
        return MessageWindowChatMemory.builder()
            .chatMemoryRepository(jdbcChatMemoryRepository)  // 对话记忆使用基于 JDBC 的存储库
            .maxMessages(10)  // 保留最近的 10 条历史记录
            .build();
    }*/

    @Bean
    public ChatMemory chatMemory(RedisChatMemoryRepository redisChatMemoryRepository) {
        return MessageWindowChatMemory.builder()
            .chatMemoryRepository(redisChatMemoryRepository)  // 对话记忆使用基于 Redis 的存储库
            .maxMessages(10)  // 保留最近的 10 条历史记录
            .build();
    }
}
