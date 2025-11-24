package com.study.chat.controller;

import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
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
    public String deepSeek(@RequestParam(value = "question") String question) {
        // 总结：System+User+Options -> Prompt -> ChatClient
        // 1. 系统角色的消息
        Message systemMessage = new SystemMessage("你是营销总监");
        // 2. 用户角色的消息
        Message userMessage = new UserMessage(question);
        // 3.模型参数
        ChatOptions chatOptions = ChatOptions.builder()
            .temperature(0.7)  // 多样化系数
//            .maxTokens(500)    // 限制 Token 用量，如是限制了 Token 用量，那么返回的模型结果可能会被截断导致返回的数据不全，按需进行使用
            .build();
        // 4. 组合 Prompt
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage), chatOptions);
        return chatClient
            .prompt(prompt)     // 提示词
            .call()             // 发送请求并获取模型生成的响应
            .content();         // 从响应中提取文本内容
    }

    @GetMapping("/ai/chat/deepseek/simple")
    public String deepSeekSimple(@RequestParam(value = "question") String question) {
        // 1.模型参数
        ChatOptions chatOptions = ChatOptions.builder()
            .temperature(0.7)  // 多样化系数
            .maxTokens(500)    // 限制 Token 用量
            .build();
        // 2. 模型调用
        return chatClient
            .prompt()               // 提示词
            .options(chatOptions)   // 系统角色
            .system("你是营销总监")   // 用户输入
            .user(question)         // 模型参数
            .call()                 // 发送请求并获取模型生成的响应
            .content();             // 从响应中提取文本内容
    }

    @GetMapping("/ai/chat/deepseek/multi_roles")
    public String deepSeekMultiRoles(@RequestParam(value = "question") String question) {
        // 1. 系统角色的消息
        Message systemMessage = new SystemMessage("你是营销总监");
        // 2. 用户角色的消息
        Message userMessage = new UserMessage(question);
        // 3. 助手角色消息
        Message assistantMessage = new AssistantMessage("要求价格价格前必须加￥");
        // 4. 组合 Prompt
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage, assistantMessage));
        // 5. 模型调用
        return chatClient
            .prompt(prompt)         // 提示词
            .call()                 // 发送请求并获取模型生成的响应
            .content();             // 从响应中提取文本内容
    }

    @GetMapping("/ai/chat/deepseek/use_memory")
    public String deepSeekUseMemory(@RequestParam(value = "question") String question) {
        // 2. 定义会话ID
        String conversationId = "Honor40";
        // 3. 基于内存保存特定会话的聊天历史
        chatMemory.add(conversationId, new AssistantMessage("要求价格价格前必须加￥"));
        // 4. 创建 Advisor 实例
        Advisor advisor = MessageChatMemoryAdvisor.builder(chatMemory)
            .conversationId(conversationId)
            .build();
        // 5. 模型调用
        return chatClient.prompt() // 提示词
            .system("你是营销总监") // 系统角色
            .advisors(advisor) // 历史顾问
            .user(question) // 用户输入
            .call() // 发送请求并获取模型生成的响应
            .content(); // 从响应中提取文本内容
    }
}
