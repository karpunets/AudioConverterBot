package com.karpunets.audio_converter_bot.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@ConfigurationProperties("storage.audio")
@Validated
public class AudioStorageProperties {
    @NotNull
    private String src;

}
