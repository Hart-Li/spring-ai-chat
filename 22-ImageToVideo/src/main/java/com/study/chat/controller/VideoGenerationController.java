package com.study.chat.controller;

import com.study.chat.utils.WanxI2V;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc TODO
 * @date 2025-12-01 20:16
 */
@RestController
public class VideoGenerationController {

    @Resource
    private WanxI2V wanxI2V;

    @PostMapping("/ai/wanxi/video")
    public String generateVideo() {
        return wanxI2V.generateVideo();
    }
}
