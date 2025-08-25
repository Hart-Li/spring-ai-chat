package com.study.chat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc 智谱AI配置
 * @date 2025-08-21 09:37
 */
@Configuration
public class ZhipuAIConfig {

    // 从配置文件中读取 api-key
    @Value("${spring.ai.zhipuai.api-key}")
    private String apiKey;

    // 从配置文件中读取 base-url
    @Value("${spring.ai.zhipuai.base-url}")
    private String baseUrl;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
            .baseUrl(baseUrl)  // 设置 baseUrl
            .defaultHeader("Authorization", "Bearer " + apiKey)  // 设置授权信息
            .build();
    }
}
