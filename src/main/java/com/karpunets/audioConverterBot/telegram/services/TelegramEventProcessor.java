package com.karpunets.audioConverterBot.telegram.services;

import com.karpunets.audioConverterBot.telegram.AudioConverterBot;
import com.karpunets.audioConverterBot.telegram.handlers.TelegramHandler;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.AbsSender;

public interface TelegramEventProcessor {

    void registerHandler(TelegramHandler handler);

    void process(Update update, AudioConverterBot converterBot);
}
