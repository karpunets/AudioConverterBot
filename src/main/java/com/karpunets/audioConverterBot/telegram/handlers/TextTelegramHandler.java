package com.karpunets.audioConverterBot.telegram.handlers;

import com.karpunets.audioConverterBot.telegram.AudioConverterBot;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.AbsSender;

@Service
public class TextTelegramHandler implements TelegramHandler {

    @Override
    public boolean support(Update update) {
        return update.hasMessage() && !update.getMessage().isCommand() && update.getMessage().hasText();
    }

    @Override
    public void handle(Update update, AudioConverterBot converterBot) {

    }
}
