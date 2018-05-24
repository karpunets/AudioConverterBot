package com.karpunets.audio_converter_bot.telegram.handlers;

import com.karpunets.audio_converter_bot.telegram.AudioConverterBot;
import com.karpunets.audio_converter_bot.telegram.commands.BotCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.Update;

import java.util.Optional;

@Service
@Slf4j
public class CommandTelegramHandler implements TelegramHandler {

    @Override
    public boolean support(Update update) {
        return update.hasMessage() && update.getMessage().isCommand();
    }

    @Override
    public void handle(Update update, AudioConverterBot bot) {
        String text = update.getMessage().getText();
        Optional<BotCommand> registry = BotCommand.Registry.getRegistry(text);
        registry.ifPresent(botCommand -> botCommand.processMessage(bot, update.getMessage()));
    }
}
