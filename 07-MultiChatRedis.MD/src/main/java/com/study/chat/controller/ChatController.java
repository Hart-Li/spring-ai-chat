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
import org.springframework.ai.chat.prompt.Prompt;
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

    @GetMapping("/ai/chat/deepseek")
    public String deepSeek(@RequestParam(value = "question") String question,
                           @RequestParam(value = "conversationId") String conversationId) {
        // 1. 生成会话ID（使用session id 确保用户隔离）
        // String conversationId = session.getId();
        if (StringUtils.isEmpty(question) || StringUtils.isEmpty(conversationId)) {
            return "error";
        }
        conversationId = "chat_memory:" + conversationId;
        // 2. 初始化系统消息(在ChatClient初始化的时候就设置了了默认的SystemMessage)
//        Message systemMessage = new SystemMessage("你是一名Java架构师，擅长精准而简洁的回答问题");
//        if (chatMemory.get(conversationId).isEmpty()) {
//            chatMemory.add(conversationId, systemMessage);  // 添加至对话记忆
//        }
        // 3. 手动获取历史消息
        List<Message> historyMessages = chatMemory.get(conversationId);
        System.out.println("historyMessages:" + historyMessages);
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

    @GetMapping("/ai/chat/deepseek/param")
    public String deepSeekParam(@RequestParam(value = "question") String question,
                                @RequestParam(value = "conversationId") String conversationId) {
        // 1. 生成会话ID（使用session id 确保用户隔离）
//        String conversationId = session.getId();
        // 2. 初始化系统消息
        Message systemMessage = new SystemMessage("你是一名Java架构师，擅长精准而简洁的回答问题");
        if (chatMemory.get(conversationId).isEmpty()) {
            chatMemory.add(conversationId, systemMessage);  // 添加至对话记忆
        }
        // 3. 手动获取历史消息
        List<Message> historyMessages = chatMemory.get(conversationId);
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
}
