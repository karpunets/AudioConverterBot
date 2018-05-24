package com.karpunets.audio_converter_bot.model;

import com.karpunets.audio_converter_bot.ffmpeg.SupportedFormat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Parent;

import javax.persistence.*;
import java.io.File;

@Embeddable
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AudioFile {
    private String fileId;
    @Convert(converter = FileConverter.class)
    private File savedFile;
    @Enumerated(EnumType.STRING)
    private SupportedFormat convertingFormat;
    @Parent
    private TelegramUser telegramUser;


    public static final class FileConverter implements AttributeConverter<File, String> {
        @Override
        public String convertToDatabaseColumn(File attribute) {
            if (attribute == null) {
                return null;
            }
            return attribute.toString();
        }

        @Override
        public File convertToEntityAttribute(String dbData) {
            if (dbData == null) {
                return null;
            }
            return new File(dbData);
        }
    }
}
