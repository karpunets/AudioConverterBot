package com.karpunets.audio_converter_bot.services.impl;

import com.karpunets.audio_converter_bot.model.AudioFile;
import com.karpunets.audio_converter_bot.properties.AudioStorageProperties;
import com.karpunets.audio_converter_bot.services.AudioStorageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class AudioStorageServiceImpl implements AudioStorageService {
    private final AudioStorageProperties audioStorageProperties;
    private long counter = 0;

    @Override
    @SneakyThrows
    public File downloadFile(String url) {
        URL source = new URL(url);
        String newFileAuthority = ++counter + source.getFile().substring(source.getFile().lastIndexOf('/') + 1);
        File newFile = Paths.get(audioStorageProperties.getSrc(), newFileAuthority).toFile();
        FileUtils.copyURLToFile(source, newFile);
        return newFile;
    }

    @Override
    public File generateOutputFile(AudioFile audioFile) {
        String fileName = ++counter + audioFile.getFileId() + audioFile.getConvertingFormat().getFileFormat();
        Path path = Paths.get(audioStorageProperties.getSrc(), fileName);
        return path.toFile();
    }
}
