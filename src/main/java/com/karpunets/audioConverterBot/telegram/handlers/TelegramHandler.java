package com.karpunets.audioConverterBot.telegram.handlers;

import com.karpunets.audioConverterBot.telegram.exceptions.TelegramHandleUpdateException;
import com.karpunets.audioConverterBot.telegram.services.TelegramEventProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.AbsSender;

/**
 * Created by Karpunets on 10.04.2018
 * Project: AudioConverterBot
 */
@Service
public interface TelegramHandler {

    boolean support(Update update);

    void handle(Update update, AbsSender absSender);

    @Autowired
    default void telegramEventProcessor(TelegramEventProcessor telegramEventProcessor) {
        telegramEventProcessor.registerHandler(this);
    }
}
