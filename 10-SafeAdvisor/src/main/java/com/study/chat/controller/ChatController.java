package com.study.chat.controller;

import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
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


    @GetMapping("/ai/chat/deepseek/safe")
    public String deepSeek(@RequestParam(value = "question") String question) {
        // 1. 创建安全顾问组件
        // 敏感词
        List<String> sensitiveWords = List.of("色情", "暴力", "有颜色的");
        // 响应信息
        String failureResponse = "无法回答此问题，请编辑后重试~";
        SafeGuardAdvisor safeGuardAdvisor = SafeGuardAdvisor.builder()
            .sensitiveWords(sensitiveWords)
            .failureResponse(failureResponse)
            .order(0)
            .build();
        // 2.请求模型并提取文本响应内容
        return chatClient.prompt()
            .user(question)
            .advisors(safeGuardAdvisor)    // 使用安全顾问
            .call()
            .content();
    }

    @GetMapping("/ai/chat/deepseek/global_safe")
    public String deepSeekGlobalSafe(@RequestParam(value = "question") String question) {
        // 1.请求模型并提取文本响应内容
        return chatClient.prompt()
            .user(question)
            .call()
            .content();
    }

}
