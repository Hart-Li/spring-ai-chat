package com.study.chat.controller;

import com.study.chat.utils.QwenASR;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc TODO
 * @date 2025-11-26 19:28
 */

@RestController
public class AudioDetectionController {

    @Resource
    private QwenASR qwenASR;

    @GetMapping("/ai/qwen/asr")
    public String getAudio() {
        String filePath = "file:///Users/HartLi/Documents/AIWorkspace/spring-ai-chat/spring-ai-chat/18-Asr/src/main/resources/audios/test.mp3";  // ABSOLUTE_PATH：文件的绝对路径
        return qwenASR.call(filePath);
    }
}
