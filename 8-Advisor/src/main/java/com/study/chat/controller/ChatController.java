package com.study.chat.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.util.StringUtils;
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

    // 1.注入 ChatMemory
    @Resource
    private ChatMemory chatMemory;

    @GetMapping("/ai/chat/deepseek/advisor")
    public String deepSeek(@RequestParam(value = "question") String question,
                           @RequestParam(value = "conversationId") String conversationId) {
        // 1. 生成会话ID（使用session id 确保用户隔离）
        // String conversationId = session.getId();
        if (StringUtils.isEmpty(question) || StringUtils.isEmpty(conversationId)) {
            return "error";
        }
        conversationId = "chat_memory:" + conversationId;
        // 2. 创建 Advisor 对象
        MessageChatMemoryAdvisor advisor = MessageChatMemoryAdvisor
            .builder(chatMemory) // 基于 chatMemory 构建顾问
            .conversationId(conversationId)
            .build();
        // 3.请求模型并提取文本响应内容
        return chatClient.prompt()
            .user(question)
            .advisors(advisor)  // 使用顾问管理对话记忆
            .call()
            .content();
    }

    @GetMapping("/ai/chat/deepseek/global_advisor")
    public String deepSeekGlobalAdvisor(@RequestParam(value = "question") String question,
                                        @RequestParam(value = "conversationId")
                                        String conversationId) {
        // 1. 生成会话ID（使用session id 确保用户隔离）
        // String conversationId = session.getId();
        if (StringUtils.isEmpty(question) || StringUtils.isEmpty(conversationId)) {
            return "error";
        }
        conversationId = "chat_memory:" + conversationId;
        // 2.请求模型并提取文本响应内容
        String finalConversationId = conversationId;
        return chatClient.prompt()
            .user(question)
            .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, finalConversationId))
            .call()
            .content();
    }

}
