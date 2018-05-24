package com.karpunets.audio_converter_bot.telegram.services;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.karpunets.audio_converter_bot.telegram.AudioConverterBot;
import com.karpunets.audio_converter_bot.telegram.handlers.TelegramHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.Update;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
    public void process(Update update, AudioConverterBot converterBot) {
        log.debug("Received {}.", update);
        handlers.forEach(handler -> {
            if (handler.support(update)) {
                CompletableFuture
                        .runAsync(() -> handler.handle(update, converterBot), handlerExecutor)
                        .exceptionally(throwable -> {
                            log.error("Error when handle " + handler.getClass().getName(), throwable);
                            return null;
                        });
            }
        });
    }
}
