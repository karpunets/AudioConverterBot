package com.karpunets.audioConverterBot.telegram.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;


@Slf4j
@Service
public class StartCommand implements BotCommand {

    public StartCommand() {
        Registry.register(this);
    }

    @Override
    public String getCommandIdentifier() {
        return "/start";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message) {
        try {
            absSender.execute(new SendMessage(message.getChatId(), "Welcome"));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
