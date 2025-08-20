package com.study.chat.controller;

import com.study.chat.utils.RealTimeSpeechRecognitionTools;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc 实时语音识别Controller
 * @date 2025-08-20 22:06
 */
@RestController
public class RealTimeSpeechRecognitionController {

    @Resource
    private RealTimeSpeechRecognitionTools realTimeSpeechRecognitionTools;

    @GetMapping("/ai/realtime/speech")
    public void realtimeSpeech() {
        realTimeSpeechRecognitionTools.recognition();
    }
}
