package com.karpunets.audio_converter_bot.ffmpeg;

import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@RequiredArgsConstructor
public enum SupportedFormat {
    FLAC(".flac"),
    MP3(".mp3"),
    WMA(".wma"),
    AIFF(".aiff"),
    ACC(".aac"),
    OGG(".ogg"),
    WV(".wv"),
    AC3(".ac3");

    private static final Set<String> names;
    private final String fileFormat;

    static {
        names = Collections.unmodifiableSet(
                stream(values())
                        .map(Enum::name)
                        .collect(Collectors.toSet())
        );
    }

    public static Set<String> names() {
        return names;
    }

    public String getFileFormat() {
        return fileFormat;
    }
}
