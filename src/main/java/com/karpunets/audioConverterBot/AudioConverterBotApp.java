package com.karpunets.audioConverterBot;

import com.karpunets.audioConverterBot.telegram.TelegramConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.Arrays;

/**
 * Created by Karpunets on 10.04.2018
 * Project: AudioConverterBot
 */
@Slf4j
@SpringBootApplication
@Import(TelegramConfiguration.class)
public class AudioConverterBotApp {
    public static void main(String[] args) {
        SpringApplication.run(AudioConverterBotApp.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> log.info("Application started with command-line arguments: {}.", Arrays.toString(args));
    }
}
