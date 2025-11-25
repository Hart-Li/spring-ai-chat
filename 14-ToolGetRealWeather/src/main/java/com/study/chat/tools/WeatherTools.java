package com.study.chat.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc TODO
 * @date 2025-11-25 20:19
 */
@Component
public class WeatherTools {

    @Value("${weather.gaode.key}")
    private String gaodeKey;

    @Tool(description = "获取指定地区的天气预报")
    String getWeather(String city) {
        // 1. 基础服务地址
        String baseUrl = "https://restapi.amap.com";
        // 2. 基于 baseUrl 构建一个 RestClient
        RestClient restClient = RestClient.builder()
            .baseUrl(baseUrl)
            .build();
        // 3. 发起 HTTP GET 请求，并使用 Map 存储响应数据
        String uri = "/v3/weather/weatherInfo?key={0}&city={1}";
        Map<?, ?> weather = restClient.get()
            .uri(uri, gaodeKey, city)
            .retrieve()
            .body(Map.class);
        // 4. Jackson 库将 Map 转换为 JSON 字符串后返回给 AI 模型
        try {
            return new ObjectMapper().writeValueAsString(weather);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
