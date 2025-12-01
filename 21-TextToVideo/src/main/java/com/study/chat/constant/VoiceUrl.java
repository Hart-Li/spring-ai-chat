package com.study.chat.constant;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc TODO
 * @date 2025-12-01 16:13
 */
@AllArgsConstructor
@Getter
public enum VoiceUrl {

    GUODEGANG("guodegang", "http://blogmedia.463644535.xyz/Guodegang.mp3");

    private final String name;
    private final String url;

    public static final Map<String, VoiceUrl> VOICE_URL_MAP = Arrays.stream(VoiceUrl.values()).collect(
        Collectors.toMap(VoiceUrl::getName, v -> v));

}
