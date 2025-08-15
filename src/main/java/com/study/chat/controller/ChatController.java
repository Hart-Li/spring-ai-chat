package com.study.chat.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc 聊天控制器
 * @date 2025-08-08 13:09
 */
@RestController
public class ChatController {

    @Resource(name = "deepseek")
    private ChatClient chatClient;

    @GetMapping("/ai/chat")
    public String chat(String question) {
        // 请求模型并提取文本响应内容
        return chatClient.prompt().user(question).call().content();
    }

}
