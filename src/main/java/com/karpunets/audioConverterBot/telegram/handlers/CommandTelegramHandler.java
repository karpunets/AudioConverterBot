package com.karpunets.audioConverterBot.telegram.handlers;

import com.karpunets.audioConverterBot.telegram.commands.BotCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.Audio;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.AbsSender;

import java.util.Optional;

/**
 * Created by Karpunets on 10.04.2018
 * Project: AudioConverterBot
 */
@Service
@Slf4j
public class CommandTelegramHandler implements TelegramHandler {

    @Override
    public boolean support(Update update) {
        return update.hasMessage() && update.getMessage().isCommand();
    }

    @Override
    public void handle(Update update, AbsSender absSender) {
        String text = update.getMessage().getText();
        Optional<BotCommand> registry = BotCommand.Registry.getRegistry(text);
        registry.ifPresent(botCommand -> botCommand.processMessage(absSender, update.getMessage()));
    }
}
