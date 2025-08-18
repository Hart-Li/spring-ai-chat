package com.study.chat.controller;

import com.study.chat.utils.QwenTTS;
import jakarta.annotation.Resource;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc 文本转语音Controller
 * @date 2025-08-18 21:40
 */
@RestController
public class AudioGenerationController {

    @Resource
    private QwenTTS qwenTTS;

    @GetMapping("/ai/qwen/tts")
    public ResponseEntity<Map<String, String>> getAudio(String content) {
        return qwenTTS.call(content);
    }
}
