package com.study.chat.controller;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc 聊天控制器
 * @date 2025-08-08 13:09
 */
@RestController
public class ChatController {

    @Resource
    private ChatModel chatModel;

    @Resource
    private ChatClient chatClient;
    @Resource
    private ChatMemory chatMemory;

    @GetMapping("/ai/chat/deepseek")
    public String deepSeek(String question, String conversationId) {
        // 1. 生成会话ID（使用session id 确保用户隔离）
        // String conversationId = session.getId();
        System.out.println("conversationId: " + conversationId);
        // 2. 初始化系统消息
        Message systemMessage = new SystemMessage("你是一名Java架构师，擅长精准而简洁的回答问题");
        if (chatMemory.get(conversationId).isEmpty()) {
            chatMemory.add(conversationId, systemMessage);  // 添加至对话记忆
        }

        // 3. 手动获取历史消息
        List<Message> historyMessages = chatMemory.get(conversationId);
        System.out.println("historyMessages: " + historyMessages);
        // 4.用户消息
        Message userMessage = new UserMessage(question);
        // 新建集合，避免污染历史消息
        List<Message> promptMessages = new ArrayList<>(historyMessages);
        // 本次用户消息合并到历史消息中
        promptMessages.add(userMessage);
        // 5. 完成 Prompt 对象
        Prompt prompt = new Prompt(promptMessages);
        // 6. 发送至 AI 模型，提取响应文本
        String responseText = chatClient.prompt(prompt).call().content();
        // 7. 本次 AI 响应添加至对话记忆（助手角色）
        chatMemory.add(conversationId, new AssistantMessage(responseText));
        // 8. 返回响应文本
        return responseText;
    }

    @GetMapping(value = "/ai/chat/stream/deepseek", produces = "text/html;charset=UTF-8")
    public Flux<String> deepSeekStream(String question) {
        return chatModel.stream(question);
    }

}
