package com.karpunets.audioConverterBot.database.model;

import com.google.common.base.Strings;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.api.objects.Message;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AudioFile {
    private Integer telegramMessageId;
    private String fileUrl;

    public AudioFile(Message message, String fileUrl) {
        this.telegramMessageId = Objects.requireNonNull(message.getMessageId());
        this.fileUrl = Objects.requireNonNull(Strings.emptyToNull(fileUrl));
    }

    public static AudioFile of(Message message, String fileUrl) {
        return new AudioFile(message, fileUrl);
    }
}
