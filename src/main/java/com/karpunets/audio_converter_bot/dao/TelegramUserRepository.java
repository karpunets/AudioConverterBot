package com.karpunets.audio_converter_bot.dao;

import com.karpunets.audio_converter_bot.model.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.telegram.telegrambots.api.objects.User;

import javax.transaction.Transactional;
import java.util.Optional;

public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {

    Optional<TelegramUser> findByTelegramId(Integer telegramId);

    @Transactional
    default TelegramUser findOrCreateUser(User user, Long chatId) {
        return findByTelegramId(user.getId()).orElseGet(() -> save(TelegramUser.of(user, chatId)));
    }
}
