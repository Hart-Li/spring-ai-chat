package com.study.chat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc 请求参数封装
 * @date 2025-08-21 09:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoGenerationRequest {

    // 模型名
    private String model = "cogvideox-flash";
    // 视频描述文本
    private String prompt;
    // 是否生成 AI 音效，实测 cogvideox-flash 模型不支持
    private Boolean withAudio = true;
    // 以下配置参数是智谱官方提供，但 cogvideox-flash 不支持
    // 默认质量优先模式，"quality"为质量优先，"speed"为生成速度优先
    private String quality = "quality";
    // 默认 1080P 分辨率（1920*1080），支持最高4K（如："3840*2160"）
    private String size = "1920*1080";
    // 默认 30 帧，支持最高60帧
    private Integer fps = 30;

}
