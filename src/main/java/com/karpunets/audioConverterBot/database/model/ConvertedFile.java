package com.karpunets.audioConverterBot.database.model;

import com.karpunets.audioConverterBot.ffmpeg.SupportedFormat;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ConvertedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    private TelegramUser owner;
    @Enumerated(EnumType.STRING)
    private SupportedFormat format;
}
