package com.study.chat.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc TODO
 * @date 2025-11-26 14:14
 */
@Configuration
public class SpringAIConfig {

    @Bean
    public ChatClient chatClient(ZhiPuAiChatModel chatModel) {
        return ChatClient.builder(chatModel)
            .defaultSystem("你是 AI 助手，回答问题简洁而明确")
            .build();
    }
}
