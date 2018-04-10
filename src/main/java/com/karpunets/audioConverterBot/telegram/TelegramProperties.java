package com.karpunets.audioConverterBot.telegram;

import lombok.Data;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import javax.validation.constraints.NotEmpty;

/**
 * Created by Karpunets on 10.04.2018
 * Project: AudioConverterBot
 */
@Data
@PropertySource("classpath:telegram.yml")
@ConfigurationProperties(prefix = "telegram")
public class TelegramProperties {
    private Bot bot;

    @Value
    public static class Bot {
        @NotEmpty
        private String username;
        @NotEmpty
        private String token;
    }
}
