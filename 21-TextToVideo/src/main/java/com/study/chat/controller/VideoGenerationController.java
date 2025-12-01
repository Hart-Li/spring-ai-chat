package com.study.chat.controller;

import com.study.chat.model.VideoGenerationRequest;
import com.study.chat.service.ZhipuAIVideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc TODO
 * @date 2025-11-26 19:28
 */

@RestController
public class VideoGenerationController {

    private final ZhipuAIVideoService zhipuAIVideoService;

    public VideoGenerationController(ZhipuAIVideoService zhipuAIVideoService) {
        this.zhipuAIVideoService = zhipuAIVideoService;
    }


    @PostMapping("/ai/zhipu/video")
    public ResponseEntity<String> generateVideo(@RequestBody VideoGenerationRequest request) {
        return zhipuAIVideoService.generateVideo(request);
    }

    @GetMapping("/ai/zhipu/video/result/{id}")
    public ResponseEntity<String> getVideoResult(@PathVariable String id) {
        return zhipuAIVideoService.retrieveVideoResult(id);
    }

}
