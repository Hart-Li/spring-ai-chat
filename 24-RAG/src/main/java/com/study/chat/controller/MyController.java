package com.study.chat.controller;

import java.util.List;
import java.util.Map;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc TODO
 * @date 2025-12-09 09:30
 */
@RestController
public class MyController {

    // 声明向量模型
    private EmbeddingModel embeddingModel;

    // 构造注入向量模型
    public MyController(OllamaEmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @GetMapping("/ollama/embedding")
    public Map embedding() {
        System.out.println("向量维度："+ embeddingModel.dimensions());
//        EmbeddingResponse response = embeddingModel.call(
//            new EmbeddingRequest(
//                Collections.singletonList("牛肉面"),  // 要嵌入的文本列表
//                OllamaOptions  // Ollama 配置选项
//                    .builder()
//                    .model("qwen3-embedding:0.6b") // 向量模型名（局部设置）
//                    .truncate(false)  // 遇到长文本不截断
//                    .build()
//                )
//        );
//        return Map.of("result", response);

//        return Map.of("result", embeddingModel.embed("牛肉面"));
//        return Map.of("result", embeddingModel.embed(List.of("兰州", "牛肉面")));
        return Map.of("result", embeddingModel.embedForResponse(List.of("兰州", "牛肉面")));
    }
}
