package com.karpunets.audioConverterBot.telegram.handlers;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.AbsSender;

/**
 * Created by Karpunets on 10.04.2018
 * Project: AudioConverterBot
 */
@Service
public class TextTelegramHandler implements TelegramHandler {

    @Override
    public boolean support(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }

    @Override
    public void handle(Update update, AbsSender absSender) {

    }
}
