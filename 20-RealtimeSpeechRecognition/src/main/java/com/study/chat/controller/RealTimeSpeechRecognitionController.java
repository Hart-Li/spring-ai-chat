package com.study.chat.controller;

import com.study.chat.tools.RealTimeSpeechRecognitionTools;
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
public class RealTimeSpeechRecognitionController {

    @Resource
    private RealTimeSpeechRecognitionTools realTimeSpeechRecognitionTools;

    @GetMapping("/ai/realtime/speech")
    public void getAudio() {
        realTimeSpeechRecognitionTools.recognition();
    }
}
