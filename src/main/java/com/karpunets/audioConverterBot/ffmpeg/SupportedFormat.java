package com.karpunets.audioConverterBot.ffmpeg;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public enum SupportedFormat {
    FLAC, AC3, DTS, MP3, WMA;

    private static final Set<String> names;

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
}
