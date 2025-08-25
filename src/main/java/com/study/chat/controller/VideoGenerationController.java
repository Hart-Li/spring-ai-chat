package com.study.chat.controller;

import com.study.chat.model.VideoGenerationRequest;
import com.study.chat.service.ZhipuAIVideoService;
import com.study.chat.utils.WanxI2V;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc 视频生成Controller
 * @date 2025-08-21 09:57
 */
@RestController
public class VideoGenerationController {

    private final ZhipuAIVideoService zhipuAIVideoService;

    private final WanxI2V wanxI2V;

    public VideoGenerationController(ZhipuAIVideoService zhipuAIVideoService, WanxI2V wanxI2V) {
        this.zhipuAIVideoService = zhipuAIVideoService;
        this.wanxI2V = wanxI2V;
    }

    @PostMapping("/ai/wanxi/video")
    public String generateVideo() {
        return wanxI2V.generateVideo();
    }

    @PostMapping("/ai/zhipu/video")
    public ResponseEntity<String> generateVideo(@RequestBody VideoGenerationRequest request) {
        return zhipuAIVideoService.generateVideo(request);
    }

    @GetMapping("/ai/zhipu/video/result/{id}")
    public ResponseEntity<String> retrieveVideoResult(@PathVariable String id) {
        return zhipuAIVideoService.retrieveVideoResult(id);
    }
}
