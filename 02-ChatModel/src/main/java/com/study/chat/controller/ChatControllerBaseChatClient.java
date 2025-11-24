package com.study.chat.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc TODO
 * @date 2025-11-24 11:44
 */
@RestController
public class ChatControllerBaseChatClient {

    @Resource
    private ChatClient chatClient;

    @GetMapping("/ai/chat_client/deepseek")
    public String deepSeek(@RequestParam(value = "question") String question) {
        return chatClient.
            prompt().           // 设置请求的上下文
                user(question).     // 设置用户输入
                call().             // 发送请求并获取模型生成的响应
                content();          // 从响应中提取文本内容
    }
}
