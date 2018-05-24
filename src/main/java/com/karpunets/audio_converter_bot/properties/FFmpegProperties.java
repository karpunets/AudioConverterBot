package com.karpunets.audio_converter_bot.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Data
@ConfigurationProperties("ffmpeg")
@Validated
public class FFmpegProperties {
    @NotEmpty
    private String ffmpegPath;
    @NotEmpty
    private String ffprobePath;

}
