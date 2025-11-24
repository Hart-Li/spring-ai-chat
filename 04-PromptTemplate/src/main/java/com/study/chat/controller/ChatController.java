package com.study.chat.controller;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
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
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.core.io.ClassPathResource;
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
    public String deepSeek(@RequestParam(value = "year") String year,
                           @RequestParam(value = "platform") String platform) {
        // 1. 原始模板
        String template = "推荐{year}年{platform}最火的音乐前3名";
        PromptTemplate promptTemplate = new PromptTemplate(template);
        // 2. 定义变量值
        promptTemplate.add("year", year);
        promptTemplate.add("platform", platform);
        // 3. 生成 prompt 对象
        Prompt prompt = promptTemplate.create();
        return chatClient
            .prompt(prompt)     // 提示词
            .call()             // 发送请求并获取模型生成的响应
            .content();         // 从响应中提取文本内容
    }

    @GetMapping("/ai/chat/deepseek/simple")
    public String deepSeekSimple(@RequestParam(value = "year") String year,
                                 @RequestParam(value = "platform") String platform) {
        // 1. 原始模板
        String template = "推荐{year}年{platform}最火的音乐前3名";
        PromptTemplate promptTemplate = PromptTemplate.builder().template(template).build();
        // 2. 定义变量值 +  3. 生成 prompt 对象
        Prompt prompt = promptTemplate.create(Map.of("year", year, "platform", platform));
        /*// 2. 定义变量值
        promptTemplate.add("year", year);
        promptTemplate.add("platform", platform);
        // 3. 生成 prompt 对象
        Prompt prompt = promptTemplate.create();*/
        return chatClient
            .prompt(prompt)     // 提示词
            .call()             // 发送请求并获取模型生成的响应
            .content();         // 从响应中提取文本内容
    }

    @GetMapping("/ai/chat/deepseek/multi_roles")
    public String deepSeekMultiRoles(@RequestParam(value = "topic") String topic) {
        // 1. 系统角色消息
        String systemText = "你是一名{role}，擅长精准而简洁得回答问题";
        PromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemText);
        String role = "Java架构师";
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("role", role));
        // 2. 用户角色消息
        Message userMessage = new UserMessage(topic);
        // 3. 组装 Prompt
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        return chatClient
            .prompt(prompt)     // 提示词
            .call()             // 发送请求并获取模型生成的响应
            .content();         // 从响应中提取文本内容
    }

    private org.springframework.core.io.Resource systemMessageResource =
        new ClassPathResource("prompts/system-message.st");

    @GetMapping("/ai/chat/deepseek/external_resource")
    public String deepSeekExternalResource(@RequestParam(value = "topic") String topic) {
        // 1. 系统角色消息
//        String systemText = "你是一名{role}，擅长精准而简洁得回答问题";
//        PromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemText);
        PromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemMessageResource);
        String role = "Java架构师";
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("role", role));
        // 2. 用户角色消息
        Message userMessage = new UserMessage(topic);
        // 3. 组装 Prompt
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        return chatClient
            .prompt(prompt)     // 提示词
            .call()             // 发送请求并获取模型生成的响应
            .content();         // 从响应中提取文本内容
    }

    @GetMapping("/ai/chat/deepseek/not_use_delimiter")
    public String deepSeekNotUseDelimiter(@RequestParam(value = "year") String year,
                                          @RequestParam(value = "platform") String platform) {
        // 1. 原始模板
        String template = "推荐{year}年{platform}最火的音乐前3名";
        PromptTemplate promptTemplate = PromptTemplate.builder().renderer(
                StTemplateRenderer.builder().startDelimiterToken('<').endDelimiterToken('>').build())
            .template(template)
            .build();
        // 2. 定义变量值 +  3. 生成 prompt 对象
        Prompt prompt = promptTemplate.create(Map.of("year", year, "platform", platform));
        return chatClient
            .prompt(prompt)     // 提示词
            .call()             // 发送请求并获取模型生成的响应
            .content();         // 从响应中提取文本内容
    }

    @GetMapping("/ai/chat/deepseek/use_delimiter")
    public String deepSeekUseDelimiter(@RequestParam(value = "year") String year,
                                       @RequestParam(value = "platform") String platform) {
        // 1. 原始模板
        String template = "推荐<year>年<platform>最火的音乐前3名";
        PromptTemplate promptTemplate = PromptTemplate.builder().renderer(
                StTemplateRenderer.builder().startDelimiterToken('<').endDelimiterToken('>').build())
            .template(template)
            .build();
        // 2. 定义变量值 +  3. 生成 prompt 对象
        Prompt prompt = promptTemplate.create(Map.of("year", year, "platform", platform));
        return chatClient
            .prompt(prompt)     // 提示词
            .call()             // 发送请求并获取模型生成的响应
            .content();         // 从响应中提取文本内容
    }
}
