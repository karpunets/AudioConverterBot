package com.karpunets.audioConverterBot.database.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Parameter;
import org.telegram.telegrambots.api.objects.User;

import javax.persistence.*;
import java.util.*;

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
    private String firstName;
    private String lastName;
    private String userName;
    private String languageCode;
    @Setter
    private AudioFile audioFile;
    @OneToMany(mappedBy = "owner", cascade = REMOVE)
    private Set<ConvertedFile> convertedFiles = new HashSet<>();

    private TelegramUser(User user) {
        telegramId = user.getId();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        languageCode = user.getLanguageCode();
    }

    public static TelegramUser of(User user) {
        return new TelegramUser(user);
    }
}
