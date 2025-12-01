package com.study.chat.controller;

import com.study.chat.tools.VoiceCloneTools;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc TODO
 * @date 2025-11-26 19:28
 */

@RestController
public class AudioCloneController {

    @Resource
    private VoiceCloneTools voiceCloneTools;

    @GetMapping("/ai/qwen/voice/clone")
    public void getAudio(@RequestParam(value = "content") String content,
                         @RequestParam(value = "voiceName") String voiceName) {
        voiceCloneTools.audioClone(content, voiceName);
    }
}
