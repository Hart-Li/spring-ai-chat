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
 * @date 2025-11-18 15:51
 */
@RestController
public class ChatController {

    @Resource(name = "deepseek")
    private ChatClient deepseekChatClient;

    @GetMapping("/ai/chat/deepseek")
    public String deepSeek(@RequestParam(value = "question") String question) {
        // 请求模型并转换为对应的实体
        return deepseekChatClient.prompt()
            .user(question)
            .call()
            .content();
    }


    @Resource(name = "zhipu")
    private ChatClient zhipuChatClient;

    @GetMapping("/ai/chat/zhipu")
    public String zhipu(@RequestParam(value = "question") String question) {
        // 请求模型并转换为对应的实体
        return zhipuChatClient.prompt()
            .user(question)
            .call()
            .content();
    }
}
