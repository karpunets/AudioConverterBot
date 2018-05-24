package com.karpunets.audio_converter_bot.telegram.commands;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.bots.AbsSender;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface BotCommand {
    String getCommandIdentifier();

    void processMessage(AbsSender absSender, Message message);

   final class Registry {
        private static Map<String, Optional<BotCommand>> references = new HashMap<>();

        private Registry() {
            throw new java.lang.UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }

        static void register(BotCommand botCommand) {
            references.put(botCommand.getCommandIdentifier(), Optional.of(botCommand));
        }

        public static Optional<BotCommand> getRegistry(String commandIdentifier) {
            return references.getOrDefault(commandIdentifier, Optional.empty());
        }
    }
}
