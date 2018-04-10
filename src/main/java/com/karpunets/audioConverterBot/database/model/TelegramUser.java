package com.karpunets.audioConverterBot.database.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import org.telegram.telegrambots.api.objects.User;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karpunets on 10.04.2018
 * Project: AudioConverterBot
 */
@Entity
@Immutable
@Getter
@Setter(AccessLevel.PROTECTED)
public class TelegramUser {
    @Id
    private Long id;
    @Column(nullable = false)
    private Integer telegramId;
    @Column(nullable = false)
    private String firstName;
    private String lastName;
    private String userName;
    private String languageCode;
    @ElementCollection
    private List<String> photos = new ArrayList<>();

    protected TelegramUser() {
    }

    public TelegramUser of(User user) {
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.telegramId = user.getId();
        telegramUser.firstName = user.getFirstName();
        telegramUser.lastName = user.getLastName();
        telegramUser.languageCode = user.getLanguageCode();
        return telegramUser;
    }
}
