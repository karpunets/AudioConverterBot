package com.karpunets.audio_converter_bot;

import com.karpunets.audio_converter_bot.properties.AudioStorageProperties;
import com.karpunets.audio_converter_bot.properties.FFmpegProperties;
import com.karpunets.audio_converter_bot.properties.TelegramProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;

import java.util.Arrays;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties({TelegramProperties.class, FFmpegProperties.class, AudioStorageProperties.class})
public class AudioConverterBotApp {
    public static void main(String[] args) {
        SpringApplication.run(AudioConverterBotApp.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> log.info("Application started with command-line arguments: {}.", Arrays.toString(args));
    }

    static {
        ApiContextInitializer.init();
    }

    @Bean
    public TelegramBotsApi telegramBotsApi() {
        return new TelegramBotsApi();
    }
}
