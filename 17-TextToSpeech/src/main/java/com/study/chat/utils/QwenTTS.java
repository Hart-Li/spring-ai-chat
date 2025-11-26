package com.study.chat.utils;

import com.alibaba.dashscope.aigc.multimodalconversation.AudioParameters;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversation;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationParam;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationResult;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc TODO
 * @date 2025-11-26 19:22
 */
@Component
public class QwenTTS {

    @Value("${spring.ai.dashscope.api-key}")
    private String apiKey;

    // 模型名称,“qwen-tts-2025-05-22” 支持更多音色配置
    private static final String MODEL = "qwen-tts";

    public ResponseEntity<Map<String, String>> call(String content) {
        String url = null;
        try {
            // 1. 初始化多模态对话服务的客户端实现
            MultiModalConversation conversation = new MultiModalConversation();
            // 2. 语音参数配置
            MultiModalConversationParam param = MultiModalConversationParam.builder()
                .model(MODEL)
                .apiKey(apiKey)
                .text(content)
                .voice(AudioParameters.Voice.CHERRY)
                .build();
            // 3. 向模型发送请求并存储模型响应结果
            MultiModalConversationResult result = conversation.call(param);
            url = result.getOutput().getAudio().getUrl();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("prompt", content, "error", e.getMessage()));
        }
        return ResponseEntity.ok(Map.of("prompt", content, "data", url));
    }
}
