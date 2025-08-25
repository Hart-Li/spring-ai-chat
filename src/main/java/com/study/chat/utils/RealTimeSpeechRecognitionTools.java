package com.study.chat.utils;

import com.alibaba.dashscope.audio.asr.recognition.Recognition;
import com.alibaba.dashscope.audio.asr.recognition.RecognitionParam;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import java.nio.ByteBuffer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc TODO
 * @date 2025-08-20 21:46
 */
@Slf4j
@Component
public class RealTimeSpeechRecognitionTools {

    // 读取 application.yml 文件中配置的 api-key
    @Value("${spring.ai.dashscope.api-key}")
    private String apiKey;

    // 模型名称
    private static final String MODEL = "paraformer-realtime-v2";

    public void recognition() {
        // 创建语音数据源
        Flowable<ByteBuffer> source = Flowable.create(
            emitter -> {
                // 定义一个异步线程来处理音频数据采集
                new Thread(() -> {
                    try {
                        // 创建音频格式
                        AudioFormat audioFormat = new AudioFormat(16000, 16, 1, true, false);
                        // 根据格式匹配默认录音设备
                        TargetDataLine targetDataLine =
                            AudioSystem.getTargetDataLine(audioFormat);
                        targetDataLine.open(audioFormat);
                        // 开始录音
                        targetDataLine.start();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        long start = System.currentTimeMillis();
                        // 录音30s并进行实时转写
                        while (System.currentTimeMillis() - start < 30000) {
                            int read = targetDataLine.read(buffer.array(), 0, buffer.capacity());
                            if (read > 0) {
                                buffer.limit(read);
                                // 将录音音频数据发送给流式识别服务
                                emitter.onNext(buffer);
                                buffer = ByteBuffer.allocate(1024);
                                // 录音速率有限，防止cpu占用过高，休眠一小会儿
                                Thread.sleep(2);
                            }
                        }
                        // 通知结束转写
                        emitter.onComplete();
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                }).start();
            },
            // 设置背压策略为缓冲，当消费者处理不过来时缓冲数据
            BackpressureStrategy.BUFFER
        );
        // 创建语音识别器
        Recognition recognition = new Recognition();
        // 构建语音识别参数
        RecognitionParam param = RecognitionParam.builder()
            .apiKey(apiKey)
            .model(MODEL)
            .format("pcm")  // 音频格式为 pcm（标准数字音频模式）
            .sampleRate(16000)  // 设置采样率 16HZ
            .build();
        try {
            // 语音识别器执行语音识别（参数，语音数据源），以流式方式进行识别
            recognition.streamCall(param, source)
                // 阻塞式遍历每一个识别结果
                .blockingForEach(result -> {
                    // 判断当前识别结果是否是一个完整句子的结尾
                    if (result.isSentenceEnd()) {
                        // 识别完成并输出句子
                        log.info("识别结果：{}", result.getSentence().getText());
                    }
                });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
