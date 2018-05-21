package com.karpunets.audioConverterBot.database.dao;

import com.karpunets.audioConverterBot.database.model.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.telegram.telegrambots.api.objects.User;

import javax.transaction.Transactional;
import java.util.Optional;

public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {

    Optional<TelegramUser> findByTelegramId(Integer telegramId);

    @Transactional
    default TelegramUser findOrCreateUser(User user) {
        return findByTelegramId(user.getId()).orElseGet(() -> save(TelegramUser.of(user)));
    }
}
