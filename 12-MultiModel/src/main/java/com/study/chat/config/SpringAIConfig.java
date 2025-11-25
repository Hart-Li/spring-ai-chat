package com.study.chat.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
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

    // 创建基于 OpenAi 模型的客户端
    @Bean(name = "deepseek")
    public ChatClient deepseekChatClient(OpenAiChatModel model) {
        return ChatClient.builder(model)
            .defaultSystem("你是 DeepSeek")
            .build();
    }

    // 创建基于 Zhipuai 模型的客户端
    @Bean(name = "zhipu")
    public ChatClient zhipuChatClient(ZhiPuAiChatModel model) {
        return ChatClient.builder(model)
            .defaultSystem("你是智谱 AI")
            .build();
    }

}
