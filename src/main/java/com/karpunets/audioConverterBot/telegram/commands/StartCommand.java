package com.karpunets.audioConverterBot.telegram.commands;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.bots.AbsSender;

/**
 * Created by Karpunets on 11.04.2018
 * Project: AudioConverterBot
 */
@Service
public class StartCommand implements BotCommand {
    @Override
    public String getCommandIdentifier() {
        return "/start";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message) {

    }
}
