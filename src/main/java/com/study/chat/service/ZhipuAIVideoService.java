package com.study.chat.service;

import com.study.chat.model.VideoGenerationRequest;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc 智谱 AI 视频服务
 * @date 2025-08-21 09:50
 */
@Service
public class ZhipuAIVideoService {

    // 注入 RestClient 对象
    @Resource
    private RestClient restClient;

    // 生成视频
    public ResponseEntity<String> generateVideo(VideoGenerationRequest request) {
        // 生成视频的API接口地址
        final String API_URL = "/v4/videos/generations";
        // 发送 POST 请求
        return restClient.post()
            .uri(API_URL)  // 配置请求路由
            .contentType(MediaType.APPLICATION_JSON)  // 请求体为 json 格式
            .body(request)  // 具体请求参数
            .retrieve()  // 执行 http 请求并获取响应
            .toEntity(String.class);  // 将响应的 JSON 解析成 String 响应体
    }

    // 查看视频（videoId）代表视频ID
    public ResponseEntity<String> retrieveVideoResult(String videoId) {
        // 查看视频的 API 接口地址
        final String API_URL = "/v4/async-result/{id}";
        // 发送 GET 请求，将ID 作为路径参数
        return restClient.get()
            .uri(API_URL, videoId)
            .retrieve()
            .toEntity(String.class);
    }
}
