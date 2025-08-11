package com.study.chat.controller;

import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.ai.chat.client.ChatClient;
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

    @GetMapping("/ai/chat/deepseek")
    public String deepSeek(String question) {
        // 1. 系统角色的消息
        Message systemMessage = new SystemMessage("你是营销总监");
        // 2. 用户角色的消息
        Message userMessage = new UserMessage(question);
        // 3. 助手角色消息
        Message assistantMessage = new AssistantMessage("要求价格价格前必须加￥");
        // 4. 组合 Prompt
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage, assistantMessage));
        return chatClient
            .prompt(prompt)     // 提示词
            .call()             // 发送请求并获取模型生成的响应
            .content();         // 从响应中提取文本内容
    }

    @GetMapping(value = "/ai/chat/stream/deepseek", produces = "text/html;charset=UTF-8")
    public Flux<String> deepSeekStream(String question) {
        return chatModel.stream(question);
    }

}
