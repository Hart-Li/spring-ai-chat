package com.study.chat.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc 视觉理解 Controller
 * @date 2025-08-18 20:26
 */
@RestController
public class ImageDetectionController {

    @Resource(name = "zhipu")
    private ChatClient chatClient;

    @Value("classpath:images/text.png")
    private org.springframework.core.io.Resource image; // 图像资源

    @GetMapping("/ai/zhipu/image_detection")
    public String imageDetection(String question) {
        return chatClient.prompt()
            .user(u -> {
                u.text(question);
                u.media(MimeTypeUtils.IMAGE_PNG, image);
            })
            .call()
            .content();
    }
}
