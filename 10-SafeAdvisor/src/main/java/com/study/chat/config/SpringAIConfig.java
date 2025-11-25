package com.study.chat.config;

import java.util.Arrays;
import java.util.List;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
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
                    .param("role", "AI 安全助手")).build();
    }
}
