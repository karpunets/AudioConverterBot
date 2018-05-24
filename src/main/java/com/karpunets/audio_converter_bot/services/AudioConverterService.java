package com.karpunets.audio_converter_bot.services;


import com.karpunets.audio_converter_bot.model.AudioFile;
import com.karpunets.audio_converter_bot.model.ConvertedFile;

import java.util.concurrent.CompletableFuture;

public interface AudioConverterService {

    CompletableFuture<ConvertedFile> convert(AudioFile audioFile);
}
