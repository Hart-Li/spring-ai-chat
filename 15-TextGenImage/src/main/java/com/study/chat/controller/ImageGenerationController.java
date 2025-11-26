package com.study.chat.controller;

import jakarta.annotation.Resource;
import java.util.Map;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.zhipuai.ZhiPuAiImageOptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc TODO
 * @date 2025-11-26 13:03
 */
@RestController
public class ImageGenerationController {

    @Resource(name = "zhiPuAiImageModel")
    private ImageModel imageModel;

    @GetMapping("/ai/zhipu/image")
    public ResponseEntity<Map<String, Object>> zhipuImageGeneration(
        @RequestParam(value = "prompt") String prompt) {
        // 1. 图像参数设置
        ImageOptions imageOptions = ZhiPuAiImageOptions.builder()
            .user("hartli")
            .model("cogview-3-flash")
            .build();
        // 2. 生图提示词
        ImagePrompt imagePrompt = new ImagePrompt(prompt, imageOptions);
        try {
            // 3. 发送生图请求 + 4. 获取模型响应
            ImageResponse imageResponse = imageModel.call(imagePrompt);
            // 5. 解析图像地址
            String url = imageResponse.getResult().getOutput().getUrl();
            // 6. 响应前端
            return ResponseEntity.ok(Map.of("prompt", prompt, "url", url));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("prompt", prompt, "error", e.getMessage()));
        }
    }
}
