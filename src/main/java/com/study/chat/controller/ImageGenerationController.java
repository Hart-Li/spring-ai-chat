package com.study.chat.controller;

import jakarta.annotation.Resource;
import java.util.Map;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImageOptionsBuilder;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc 文生图Controller
 * @date 2025-08-17 23:44
 */
@RestController
public class ImageGenerationController {

    @Resource(name = "zhiPuAiImageModel")
    private ImageModel imageModel;

    @GetMapping("/ai/zhihu/image")
    public ResponseEntity<Map<String, Object>> getImage(
        @RequestParam(defaultValue = "毛和狗") String prompt) {
        // 1. 图像参数
        /*ImageOptions imageOptions = ZhiPuAiImageOptions.builder()
            .user("hartli")
            .model("cogview-3-flash")
            .build();*/
        ImageOptions imageOptions = ImageOptionsBuilder.builder()
            .model("cogview-3-flash")       // 使用模型
            .width(1024)                    // 图片宽度，智谱 AI 不支持
            .height(1024)                   // 图片高度，智谱 AI 不支持
            .N(2)                           // 一次生成 N 图，智谱 AI 不支持
            .style("netural")               // 图像风格，natural-自然风格，vivid-鲜艳风格，智谱 AI 不支持
            .responseFormat("Json")         // 响应格式，智谱 AI 不支持
            .build();
        // 2. 生图提示词
        ImagePrompt imagePrompt = new ImagePrompt(prompt, imageOptions);
        try {
            // 3. 发送生图请求 + 4. 获取模型响应
            ImageResponse response = imageModel.call(imagePrompt);
            // 5. 解析图像地址
            String url = response.getResult().getOutput().getUrl();
            // 6. 响应前端
            return ResponseEntity.ok(Map.of("prompt", prompt, "data", url));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("prompt", prompt, "data", e.getMessage()));
        }
    }
}
