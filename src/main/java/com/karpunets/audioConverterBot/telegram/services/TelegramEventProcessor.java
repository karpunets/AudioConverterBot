package com.karpunets.audioConverterBot.telegram.services;

import com.karpunets.audioConverterBot.telegram.handlers.TelegramHandler;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.AbsSender;

/**
 * Created by Karpunets on 10.04.2018
 * Project: AudioConverterBot
 */
public interface TelegramEventProcessor {

    void registerHandler(TelegramHandler handler);

    void process(Update update, AbsSender absSender);
}
