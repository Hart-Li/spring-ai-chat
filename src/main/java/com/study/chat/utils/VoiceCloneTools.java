package com.study.chat.utils;

import static com.study.chat.common.VoiceUrl.VOICE_URL_MAP;

import com.alibaba.dashscope.audio.ttsv2.SpeechSynthesisParam;
import com.alibaba.dashscope.audio.ttsv2.SpeechSynthesizer;
import com.alibaba.dashscope.audio.ttsv2.enrollment.Voice;
import com.alibaba.dashscope.audio.ttsv2.enrollment.VoiceEnrollmentService;
import com.study.chat.common.VoiceUrl;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc 音频克隆
 * @date 2025-08-20 20:49
 */
@Slf4j
@Component
public class VoiceCloneTools {

    // 读取 application.yml 文件中配置的 api-key
    @Value("${spring.ai.dashscope.api-key}")
    private String apiKey;

    // 模型名称
    private static final String MODEL = "cosyvoice-v2";

    // 自定义音色前缀（作为音色管理的分类标识符）
    private static final String PREFIX = "myVoice";

    // 根据输入的文本转换为语音文件
    public void audioClone(String content, String voiceName) {
        try {
            // 1. 根据音色复核声音
            VoiceEnrollmentService service = new VoiceEnrollmentService(apiKey);
            // 根据传入的音色匹配 url
            VoiceUrl voiceUrl = VOICE_URL_MAP.get(voiceName);
            if (voiceUrl == null) {
                log.error("音色不存在");
                return;
            }
            Voice voice = service.createVoice(MODEL, PREFIX, voiceUrl.getUrl());
            // 2. 模型合成语音
            SpeechSynthesisParam param = SpeechSynthesisParam.builder()
                .apiKey(apiKey)
                .model(MODEL)
                .voice(voice.getVoiceId())
                .build();
            SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer(param, null);  // 非流式第二个参数设置为空
            ByteBuffer audio = speechSynthesizer.call(content);
            // 3. 空值校验
            if (audio == null) {
                throw new RuntimeException("语音合成失败");
            }
            // 4. 保存语音文件
            File file = new File("src/main/resources/audios/output/result.mp3");
            if (!file.exists()) {
                boolean created = file.getParentFile().mkdirs();
                if (!created) {
                    throw new RuntimeException("创建目录失败");
                }
                created = file.createNewFile();
                if (!created) {
                    throw new RuntimeException("创建文件失败");
                }
            }
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(audio.array());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
