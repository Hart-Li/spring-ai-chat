package com.study.chat.utils;

import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversation;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationParam;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationResult;
import com.alibaba.dashscope.common.MultiModalMessage;
import com.alibaba.dashscope.common.Role;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc ASR 工具类
 * @date 2025-08-19 16:43
 */
@Component
public class QwenASR {

    // 读取 application.yml 文件中配置的 api-key
    @Value("${spring.ai.dashscope.api-key}")
    private String apiKey;

    // 模型名称,“qwen-tts-2025-05-22” 支持更多音色配置
    private static final String MODEL = "qwen-audio-asr";

    // 根据输入的文本转换为语音文件
    public String call(String filePath) {
        try {
            // 1. 初始化多模态对话服务的客户端实现
            MultiModalConversation conversation = new MultiModalConversation();
            // 2. 多模态消息对象
            MultiModalMessage msg = MultiModalMessage.builder()
                .role(Role.USER.getValue())
                .content(List.of(Collections.singletonMap("audio", filePath)))
                .build();
            // 3. 请求参数配置
            MultiModalConversationParam param = MultiModalConversationParam.builder()
                .model(MODEL)
                .apiKey(apiKey)
                .message(msg)
                .build();
            // 4. 向模型发送请求并存储模型响应结果
            MultiModalConversationResult result = conversation.call(param);
            // 5. 响应结果转换为 JSON 文本
            return result.getOutput().getChoices().get(0).getMessage().getContent().get(0).get("text").toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "识别异常！";
        }
    }
}
