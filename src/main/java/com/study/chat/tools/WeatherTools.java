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
 * @desc 天气工具
 * @date 2025-08-17 23:06
 */
@Component
public class WeatherTools {

    @Value("${weather.gaode.key}")
    private String apiKey;

    @Tool(description = "获取指定地区的天气预报")
    String getCurrentWeather(String city) {
        // 1. 基础服务地址
        String baseUrl = "https://restapi.amap.com";

        // 2. 基于 baseUrl 构建一个 RestClient
        RestClient restClient = RestClient.builder().baseUrl(baseUrl).build();

        // 3. 发起 HTTP GET 请求，并使用 Map 存储响应数据
        String uri = "/v3/weather/weatherInfo?city={0}&key={1}";
        Map<?, ?> result = restClient.get().uri(uri, city, apiKey)
            .retrieve()  // 执行 http 请求并获取响应
            .body(Map.class);  // 将响应的 JSON 解析成 Map 对象

        // 4. Jackson 库将 Map 转换为 JSON 字符串后返回给 AI 模型
        try {
            String jsonStr = new ObjectMapper().writeValueAsString(result);
            return jsonStr;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
