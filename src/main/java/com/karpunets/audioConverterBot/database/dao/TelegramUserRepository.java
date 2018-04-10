package com.karpunets.audioConverterBot.database.dao;

import com.karpunets.audioConverterBot.database.model.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Karpunets on 10.04.2018
 * Project: AudioConverterBot
 */
public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {
}
