package com.karpunets.audioConverterBot.telegram;

import lombok.Data;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

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
}
