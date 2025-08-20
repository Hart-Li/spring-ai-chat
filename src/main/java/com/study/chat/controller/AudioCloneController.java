package com.study.chat.controller;

import com.study.chat.utils.VoiceCloneTools;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc 语音克隆Controller
 * @date 2025-08-20 21:17
 */
@RestController
public class AudioCloneController {

    @Resource
    private VoiceCloneTools voiceCloneTools;

    @GetMapping("/ai/voice/clone")
    public void audioClone(String content, String voiceName) {
        voiceCloneTools.audioClone(content, voiceName);
    }
}
