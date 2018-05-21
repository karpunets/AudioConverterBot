package com.karpunets.audioConverterBot.ffmpeg;

import com.karpunets.audioConverterBot.services.AudioConverterService;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.Set;

@Service
public class FfmpegConverterService implements AudioConverterService {
    @Override
    public byte[] convert(String fileUrl, String type) {
        return new byte[0];
    }

    @Override
    public Set<String> getSupportedType() {
        return SupportedFormat.names();
    }
}
