package com.karpunets.audio_converter_bot.services;

import com.karpunets.audio_converter_bot.model.AudioFile;

import java.io.File;

public interface AudioStorageService {

    File downloadFile(String url);

    File generateOutputFile(AudioFile audioFile);
}
