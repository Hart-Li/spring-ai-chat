package com.study.chat.utils;

import com.alibaba.dashscope.aigc.videosynthesis.VideoSynthesis;
import com.alibaba.dashscope.aigc.videosynthesis.VideoSynthesisParam;
import com.alibaba.dashscope.aigc.videosynthesis.VideoSynthesisResult;
import com.alibaba.dashscope.utils.JsonUtils;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc 图生视频
 * @date 2025-08-22 09:36
 */
@Component
public class WanxI2V {

    // 读取 application.yml 文件中配置的 api-key
    @Value("${spring.ai.dashscope.api-key}")
    private String apiKey;

    // 模型名称
    private static final String MODEL = "wanx2.1-kf2v-plus";

    @Value("classpath:images/first.png")
    private Resource firstImage;
    @Value("classpath:images/last.png")
    private Resource lastImage;

    // 生成视频
    public String generateVideo() {
        // 1. 创建视频合成工具
        VideoSynthesis videoSynthesis = new VideoSynthesis();
        // 2. 创建视频处理参数
        Map<String, Object> params = new HashMap<>();
        // 开启 prompt 智能改写，会增加耗时，默认为 true
        params.put("prompt_extend", true);
        // 3. 首尾帧图像URL
        String firstFrameUrl = null;
        String lastFrameUrl = null;
        try {
            firstFrameUrl = "file:///" + firstImage.getFile().getAbsolutePath().replace("\\", "/");
            lastFrameUrl = "file:///" + lastImage.getFile().getAbsolutePath().replace("\\", "/");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 4. 创建视频参数
        VideoSynthesisParam videoSynthesisParam = VideoSynthesisParam.builder()
            .apiKey(apiKey)
            .model(MODEL)
            .prompt("镜头从平视逐渐上升推进，最后俯拍")
            .firstFrameUrl(firstFrameUrl)
            .lastFrameUrl(lastFrameUrl)
            .parameters(params)
            .build();
        // 5. 存储视频合成的结果
        VideoSynthesisResult result = null;
        try {
            // 6. 发送请求并获取响应结果
            result = videoSynthesis.call(videoSynthesisParam);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 7. 返回
        return JsonUtils.toJson(result);
    }
}
