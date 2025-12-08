package com.study.chat.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc TODO
 * @date 2025-12-08 23:38
 */
@RestController
public class OllamaController {


    @Resource
    private ChatClient chatClient;

    @GetMapping(value = "/ai/ollama/zhipu", produces = "text/html;charset=UTF-8")
    public Flux<String> hello(String question) {
        return chatClient.prompt()
            .user(question)
            .stream()
            .content();
    }
}
