package com.karpunets.audio_converter_bot.ffmpeg;

import com.karpunets.audio_converter_bot.model.AudioFile;
import com.karpunets.audio_converter_bot.model.ConvertedFile;
import com.karpunets.audio_converter_bot.properties.FFmpegProperties;
import com.karpunets.audio_converter_bot.services.AudioConverterService;
import com.karpunets.audio_converter_bot.services.AudioStorageService;
import lombok.SneakyThrows;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.concurrent.CompletableFuture;

@Service
public class FFmpegConverterService implements AudioConverterService {
    private final FFmpegExecutor executor;
    private final AudioStorageService audioStorageService;

    @SneakyThrows
    public FFmpegConverterService(FFmpegProperties ffmpegProperties, AudioStorageService audioStorageService) {
        FFmpeg ffmpeg = new FFmpeg(ffmpegProperties.getFfmpegPath());
        FFprobe ffprobe = new FFprobe(ffmpegProperties.getFfprobePath());
        executor = new FFmpegExecutor(ffmpeg, ffprobe);
        this.audioStorageService = audioStorageService;
    }

    @SneakyThrows
    @Override
    public CompletableFuture<ConvertedFile> convert(AudioFile audioFile) {
        File outputFile = audioStorageService.generateOutputFile(audioFile);
        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(audioFile.getSavedFile().getCanonicalPath())
                .addOutput(outputFile.getCanonicalPath())
                .done();

        CompletableFuture<ConvertedFile> completableFuture = new CompletableFuture<>();
        FFmpegJob job = executor.createJob(builder, progress -> {
            if (progress.isEnd()) {
                completableFuture.complete(ConvertedFile.from(audioFile, outputFile));
            }
        });
        try {
            job.run();
        } catch (Exception e) {
            completableFuture.completeExceptionally(e);
        }
        return completableFuture.thenApply(convertedFile -> {
            convertedFile.setState(job.getState());
            return convertedFile;
        });
    }
}
