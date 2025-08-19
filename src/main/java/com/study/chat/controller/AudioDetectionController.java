package com.study.chat.controller;

import com.study.chat.utils.QwenASR;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc 语音识别Controller
 * @date 2025-08-19 16:53
 */
@RestController
public class AudioDetectionController {

    @Resource
    private QwenASR qwenASR;

    @GetMapping("/ai/qwen/asr")
    public String qwenAsr() {
        String filePath = "src/main/resources/audios/test.mp3";
        return qwenASR.call(filePath);
    }
}
