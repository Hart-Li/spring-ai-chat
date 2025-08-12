package com.study.chat.controller;

import jakarta.annotation.Resource;
import java.util.Map;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.template.st.StTemplateRenderer;
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
    public String deepSeek(String year, String platform) {
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

    @GetMapping(value = "/ai/chat/stream/deepseek", produces = "text/html;charset=UTF-8")
    public Flux<String> deepSeekStream(String question) {
        return chatModel.stream(question);
    }

}
