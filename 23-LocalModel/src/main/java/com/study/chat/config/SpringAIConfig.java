package com.study.chat.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc TODO
 * @date 2025-12-08 23:38
 */
@Configuration
public class SpringAIConfig {

    @Bean
    public ChatClient chatClient(OllamaChatModel model){
        return ChatClient.builder(model).build();
    }
}


