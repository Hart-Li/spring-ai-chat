package com.study.chat.utils;

import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversation;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationParam;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationResult;
import com.alibaba.dashscope.common.MultiModalMessage;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.utils.JsonUtils;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc TODO
 * @date 2025-12-01 15:28
 */
@Component
public class QwenASR {

    @Value("${spring.ai.dashscope.api-key}")
    private String apiKey;

    // 模型名称
    private static final String MODEL = "qwen-audio-asr";

    // 根据输入的文本转换为语音文件
    public String call(String filePath) {
        try {
            // 1. 初始化多模态对话服务的客户端实现
            MultiModalConversation conversation = new MultiModalConversation();
            // 2. 多模态消息对象
            MultiModalMessage message = MultiModalMessage.builder()
                .role(Role.USER.getValue())
                .content(List.of(Collections.singletonMap("audio", filePath)))
                .build();
            // 3. 请求参数配置
            MultiModalConversationParam param = MultiModalConversationParam.builder()
                .model(MODEL)
                .apiKey(apiKey)
                .message(message)
                .build();
            // 4. 向模型发送请求并存储模型响应结果
            MultiModalConversationResult result = conversation.call(param);
            return JsonUtils.toJson(result);
        } catch (Exception e) {
            e.printStackTrace();
            return "识别异常！";
        }
    }
}
