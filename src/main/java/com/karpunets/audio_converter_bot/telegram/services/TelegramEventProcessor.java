package com.karpunets.audio_converter_bot.telegram.services;

import com.karpunets.audio_converter_bot.telegram.AudioConverterBot;
import com.karpunets.audio_converter_bot.telegram.handlers.TelegramHandler;
import org.telegram.telegrambots.api.objects.Update;

public interface TelegramEventProcessor {

    void registerHandler(TelegramHandler handler);

    void process(Update update, AudioConverterBot converterBot);
}
