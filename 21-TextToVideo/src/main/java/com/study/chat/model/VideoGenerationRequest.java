package com.study.chat.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc TODO
 * @date 2025-12-01 19:42
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonDeserialize(builder = VideoGenerationRequest.VideoGenerationRequestBuilder.class)
public class VideoGenerationRequest {

    // 模型名
    @Builder.Default
    private String model = "cogvideox-flash";
    // 视频描述文本
    private String prompt;
    // 是否生成 AI 音效，实测 cogvideox-flash 模型不支持
    @Builder.Default
    private Boolean withAudio = true;
    // 以下配置参数是智谱官方提供，但 cogvideox-flash 不支持
    // 默认质量优先模式，"quality"为质量优先，"speed"为生成速度优先
    @Builder.Default
    private String quality = "quality";
    // 默认 1080P 分辨率（1920*1080），支持最高4K（如："3840*2160"）
    @Builder.Default
    private String size = "1920*1080";
    // 默认 30 帧，支持最高60帧
    @Builder.Default
    private Integer fps = 30;

    @JsonPOJOBuilder(withPrefix = "")
    public static class VideoGenerationRequestBuilder {
    }
}
