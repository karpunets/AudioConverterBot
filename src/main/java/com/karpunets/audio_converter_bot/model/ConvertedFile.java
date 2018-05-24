package com.karpunets.audio_converter_bot.model;

import com.karpunets.audio_converter_bot.ffmpeg.SupportedFormat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bramp.ffmpeg.job.FFmpegJob;

import javax.persistence.*;
import java.io.File;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConvertedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    private TelegramUser owner;
    @Enumerated(EnumType.STRING)
    private SupportedFormat format;
    @Enumerated(EnumType.STRING)
    private FFmpegJob.State state;
    @Transient
    private File file;

    public static ConvertedFile from(AudioFile audioFile, File file) {
        ConvertedFile convertedFile = new ConvertedFile();
        convertedFile.owner = audioFile.getTelegramUser();
        convertedFile.format = audioFile.getConvertingFormat();
        convertedFile.file = file;

        audioFile.setConvertingFormat(null);
        audioFile.setSavedFile(null);
        return convertedFile;
    }
}
