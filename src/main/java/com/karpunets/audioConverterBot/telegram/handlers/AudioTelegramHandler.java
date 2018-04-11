package com.karpunets.audioConverterBot.telegram.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.Audio;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.AbsSender;

/**
 * Created by Karpunets on 10.04.2018
 * Project: AudioConverterBot
 */
@Service
@Slf4j
public class AudioTelegramHandler implements TelegramHandler {

    @Override
    public boolean support(Update update) {
        return update.hasMessage() && update.getMessage().getAudio() != null;
    }

    @Override
    public void handle(Update update, AbsSender absSender) {
        Audio audio = update.getMessage().getAudio();
        log.info("Get voice fileId={} mimeType={}", audio.getFileId(), audio.getMimeType());
    }
}
