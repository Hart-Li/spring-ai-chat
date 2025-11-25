package com.study.chat.controller;

import com.study.chat.model.PhoneRecord;
import com.study.chat.model.PhoneRecordList;
import com.study.chat.model.PhoneRecordMap;
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

    @Resource
    private ChatClient chatClient;


    @GetMapping("/ai/chat/deepseek/structured/bean")
    public PhoneRecord deepSeek(@RequestParam(value = "question") String question) {
        // 请求模型并转换为对应的实体
        return chatClient.prompt()
            .user(question)
            .call()
            .entity(PhoneRecord.class);
    }

    @GetMapping("/ai/chat/deepseek/structured/list")
    public PhoneRecordList deepSeekStructuredList(
        @RequestParam(value = "question") String question) {
        // 请求模型并转换为对应的实体
        return chatClient.prompt()
            .user(question)
            .call()
            .entity(PhoneRecordList.class);
    }

    @GetMapping("/ai/chat/deepseek/structured/map")
    public PhoneRecordMap deepSeekStructuredMap(@RequestParam(value = "question") String question) {
        // 请求模型并转换为对应的实体
        return chatClient.prompt()
            .user(question)
            .call()
            .entity(PhoneRecordMap.class);
    }
}
