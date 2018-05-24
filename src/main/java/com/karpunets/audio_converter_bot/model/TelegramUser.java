package com.karpunets.audio_converter_bot.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.telegram.telegrambots.api.objects.User;

import javax.persistence.*;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static javax.persistence.CascadeType.REMOVE;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TelegramUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private Integer telegramId;
    @Column(nullable = false)
    private Long chatId;
    @Column(nullable = false)
    private String firstName;
    private String lastName;
    private String userName;
    private String languageCode;
    private AudioFile audioFile = new AudioFile();
    @OneToMany(mappedBy = "owner", cascade = REMOVE)
    private Set<ConvertedFile> convertedFiles = new HashSet<>();

    private TelegramUser(User user, Long chatId) {
        telegramId = user.getId();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        languageCode = user.getLanguageCode();
        this.audioFile = new AudioFile();
        this.chatId = chatId;
    }

    public static TelegramUser of(User user, Long chatId) {
        return new TelegramUser(user, chatId);
    }

    public void refreshAudioFile(String fileId, File file) {
        audioFile.setFileId(requireNonNull(fileId));
        audioFile.setSavedFile(requireNonNull(file));
    }

    public boolean isReadyToConvert() {
        return audioFile.getSavedFile() != null && audioFile.getConvertingFormat() != null;
    }
}
