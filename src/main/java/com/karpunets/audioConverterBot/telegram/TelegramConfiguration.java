package com.karpunets.audioConverterBot.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;

import java.util.Arrays;

/**
 * Created by Karpunets on 10.04.2018
 * Project: AudioConverterBot
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(TelegramProperties.class)
public class TelegramConfiguration {

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> log.info("Application started with command-line arguments: {}.", Arrays.toString(args));
    }

    @Bean
    public TelegramBotsApi telegramBotsApi() {
        ApiContextInitializer.init();
        return new TelegramBotsApi();
    }
}