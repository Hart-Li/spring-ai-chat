package com.study.chat.config;

import com.study.chat.tools.DateTimeTools;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
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
    private DateTimeTools dateTimeTools;

    // 创建基于 OpenAi 模型的客户端
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
            .defaultTools(dateTimeTools)
            .build();
    }
}
