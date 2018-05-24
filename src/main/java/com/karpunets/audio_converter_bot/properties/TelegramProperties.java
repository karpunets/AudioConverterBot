package com.karpunets.audio_converter_bot.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ConfigurationProperties("telegram")
@Validated
public class TelegramProperties {
    @Valid
    @NotNull
    private Bot bot;

    @Data
    public static class Bot {
        @NotEmpty
        private String username;
        @NotEmpty
        private String token;

        public void setUsername(String username) {
            this.username = username;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }
}
