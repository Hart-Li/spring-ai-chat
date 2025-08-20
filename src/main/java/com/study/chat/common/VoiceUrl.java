package com.study.chat.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc TODO
 * @date 2025-08-20 21:04
 */
@Getter
@AllArgsConstructor
public enum VoiceUrl {

    GUODEGANG("guodegang", "http://media.463644535.xyz/Guodegang.mp3");

    private final String name;
    private final String url;

    public static final Map<String, VoiceUrl> VOICE_URL_MAP = Arrays.stream(VoiceUrl.values()).collect(
        Collectors.toMap(VoiceUrl::getName, v -> v));
}
