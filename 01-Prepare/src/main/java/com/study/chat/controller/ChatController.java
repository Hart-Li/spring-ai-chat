package com.study.chat.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc TODO
 * @date 2025-11-18 15:51
 */
@RestController
public class ChatController {

    @Resource
    private ChatModel chatModel;

    @GetMapping("/ai/chat/deepseek")
    public String deepSeek(@RequestParam(value = "question") String question) {
        return chatModel.call(question);
    }
}
