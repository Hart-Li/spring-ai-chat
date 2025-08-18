package com.study.chat.utils;

import com.alibaba.dashscope.aigc.multimodalconversation.AudioParameters;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversation;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationParam;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationResult;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc Qwen TTS 转换工具
 * @date 2025-08-18 21:31
 */
@Slf4j
@Component
public class QwenTTS {

    // 读取 application.yml 文件中配置的 api-key
    @Value("${spring.ai.dashscope.api-key}")
    private String apiKey;

    // 模型名称,“qwen-tts-2025-05-22” 支持更多音色配置
    private static final String MODEL = "qwen-tts-2025-05-22";

    // 根据输入的文本转换为语音文件
    public ResponseEntity<Map<String ,String>> call(String content) {
        String url = null;   // 语音文件的 url
        try {
            // 1. 初始化多模态对话服务的客户端实现
            MultiModalConversation conversation = new MultiModalConversation();
            // 2. 语音参数配置
            MultiModalConversationParam param = MultiModalConversationParam.builder()
                .model(MODEL)
                .apiKey(apiKey)
                .text(content)
                .voice(AudioParameters.Voice.JADA)
                .build();
            // 3. 向模型发送请求并存储模型响应结果
            MultiModalConversationResult result = conversation.call(param);
            // 4. 从结果中解析出语音文件的 url
            url = result.getOutput().getAudio().getUrl();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("prompt", content, "data", e.getMessage()));
        }
        // 5. 正常返回
        return ResponseEntity.ok(Map.of("prompt", content, "data", url));
    }
}
