package com.karpunets.audio_converter_bot.telegram.handlers;

import com.karpunets.audio_converter_bot.dao.ConvertedFileRepository;
import com.karpunets.audio_converter_bot.dao.TelegramUserRepository;
import com.karpunets.audio_converter_bot.model.TelegramUser;
import com.karpunets.audio_converter_bot.services.AudioConverterService;
import com.karpunets.audio_converter_bot.services.AudioStorageService;
import com.karpunets.audio_converter_bot.telegram.AudioConverterBot;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.Voice;

@Service
@Slf4j
@RequiredArgsConstructor
public class VoiceTelegramHandler implements TelegramHandler {
    private final AudioConverterService audioConverterService;
    private final AudioStorageService audioStorageService;
    private final TelegramUserRepository telegramUserRepository;
    private final ConvertedFileRepository convertedFileRepository;

    @Override
    public boolean support(Update update) {
        return update.hasMessage() && update.getMessage().getVoice() != null;
    }

    @Override
    @SneakyThrows
    public void handle(Update update, AudioConverterBot bot) {
        Message message = update.getMessage();
        Voice voice = message.getVoice();

        TelegramUser telegramUser = telegramUserRepository.findOrCreateUser(message.getFrom(), message.getChatId());
        TelegramHandler.refreshUserAudioFile(voice.getFileId(), message, telegramUser, bot, audioStorageService);

        if (telegramUser.isReadyToConvert()) {
            TelegramHandler.tryConvert(message, telegramUser, bot, audioConverterService, convertedFileRepository);
        } else {
            TelegramHandler.sendFormatKeyboard(bot, message);
        }

        telegramUserRepository.save(telegramUser);
        log.debug("Get voice from {} mimeType={}", telegramUser.getFirstName(), voice.getMimeType());
    }
}
