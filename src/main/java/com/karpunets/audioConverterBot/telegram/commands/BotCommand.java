package com.karpunets.audioConverterBot.telegram.commands;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.bots.AbsSender;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Karpunets on 10.04.2018
 * Project: AudioConverterBot
 */
public interface BotCommand {

    String getCommandIdentifier();

    void processMessage(AbsSender absSender, Message message);

    class Registry {
        private static Map<String, Optional<BotCommand>> references = new HashMap<>();

        public static void register(BotCommand botCommand) {
            references.put(botCommand.getCommandIdentifier(), Optional.of(botCommand));
        }

        public static Optional<BotCommand> getRegistry(String commandIdentifier) {
            return references.getOrDefault(commandIdentifier, Optional.empty());
        }
    }
}
