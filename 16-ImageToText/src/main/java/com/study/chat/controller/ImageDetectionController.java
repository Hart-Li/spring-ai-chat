package com.study.chat.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc TODO
 * @date 2025-11-26 14:16
 */
@RestController
public class ImageDetectionController {

    @Resource
    private ChatClient chatClient;

    @Value("classpath:/images/girl.png")
    private org.springframework.core.io.Resource girlImage;

    @GetMapping("/ai/zhipu/image_detection")
    public String imageDetection(@RequestParam(value = "question") String question) {
        return chatClient.prompt()
            .user(u -> {
                u.text(question);
                u.media(MimeTypeUtils.IMAGE_PNG, girlImage);
            })
            .call().content();
    }

}
