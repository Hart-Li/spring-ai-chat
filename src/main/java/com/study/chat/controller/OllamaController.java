package com.study.chat.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc Ollama 控制器
 * @date 2025-08-26 00:09
 */
@RestController
public class OllamaController {


    @Resource(name = "ollama")
    private ChatClient chatClient;

    @GetMapping(value = "/ai/ollama/zhipu", produces = "text/html;charset=UTF-8")
    public Flux<String> hello(String question) {
        return chatClient.prompt()
            .user(question)
            .stream()
            .content();
    }
}
