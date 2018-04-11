package com.karpunets.audioConverterBot.telegram;

import lombok.Data;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

/**
 * Created by Karpunets on 10.04.2018
 * Project: AudioConverterBot
 */
@Data
@ConfigurationProperties("telegram")
public class TelegramProperties {
    @NotEmpty
    private Bot bot;

    @Data
    public static class Bot {
        @NotEmpty
        private String username;
        @NotEmpty
        private String token;
    }

//    public void setBot(Bot bot) {
//        this.bot = bot;
//    }
}
