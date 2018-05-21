package com.karpunets.audioConverterBot.telegram.handlers;

import com.karpunets.audioConverterBot.database.dao.TelegramUserRepository;
import com.karpunets.audioConverterBot.database.model.AudioFile;
import com.karpunets.audioConverterBot.database.model.TelegramUser;
import com.karpunets.audioConverterBot.telegram.AudioConverterBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.Audio;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;

@Slf4j
@Service
@RequiredArgsConstructor
public class AudioTelegramHandler implements TelegramHandler {
    private final TelegramUserRepository telegramUserRepository;

    @Override
    public boolean support(Update update) {
        return update.hasMessage() && update.getMessage().getAudio() != null;
    }

    @Override
    public void handle(Update update, AudioConverterBot converterBot) {
        Message message = update.getMessage();
        Audio audio = message.getAudio();
        User from = message.getFrom();
        TelegramUser telegramUser = telegramUserRepository.findOrCreateUser(from);
        telegramUser.setAudioFile(AudioFile.of(message, audio.getFileId()));
        telegramUserRepository.save(telegramUser);

        log.info("Get voice fileId={} mimeType={}", audio.getFileId(), audio.getMimeType());
    }
}
