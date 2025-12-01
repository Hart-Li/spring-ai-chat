package com.study.chat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc TODO
 * @date 2025-12-01 19:40
 */
@Configuration
public class ZhipuAIConfig {

    @Value("${spring.ai.zhipuai.api-key}")
    private String apiKey;

    @Value("${spring.ai.zhipuai.base-url}")
    private String baseUrl;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader("Authorization", "Bearer " + apiKey)
            .build();
    }
}
