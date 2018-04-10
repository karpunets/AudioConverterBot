package com.karpunets.audioConverterBot.telegram.services;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.karpunets.audioConverterBot.telegram.exceptions.TelegramHandleUpdateException;
import com.karpunets.audioConverterBot.telegram.handlers.TelegramHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Karpunets on 10.04.2018
 * Project: AudioConverterBot
 */
@Service
@Slf4j
public class TelegramEventProcessorImpl implements TelegramEventProcessor {
    private final Set<TelegramHandler> handlers = new HashSet<>();
    private final Executor handlerExecutor = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder()
            .setNameFormat("HandleUpdateTask-%d")
            .setDaemon(true)
            .build());

    @Override
    public void registerHandler(TelegramHandler handler) {
        log.info("Register {}.", handler.getClass().getName());
        handlers.add(handler);
    }

    @Override
    public void process(Update update, AbsSender absSender) {
        log.debug("Received {}.", update);
        handlers.forEach(handler -> {
            if (handler.support(update)) {
                CompletableFuture
                        .runAsync(() -> handler.handle(update, absSender), handlerExecutor)
                        .exceptionally(throwable -> {
                            log.error("Error when handle " + handler.getClass().getName(), throwable);
                            return null;
                        });
            }
        });
    }
}
