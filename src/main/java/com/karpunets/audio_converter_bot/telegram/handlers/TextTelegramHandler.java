package com.karpunets.audio_converter_bot.telegram.handlers;

import com.karpunets.audio_converter_bot.dao.ConvertedFileRepository;
import com.karpunets.audio_converter_bot.dao.TelegramUserRepository;
import com.karpunets.audio_converter_bot.model.TelegramUser;
import com.karpunets.audio_converter_bot.ffmpeg.SupportedFormat;
import com.karpunets.audio_converter_bot.services.AudioConverterService;
import com.karpunets.audio_converter_bot.telegram.AudioConverterBot;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

@Service
@RequiredArgsConstructor
public class TextTelegramHandler implements TelegramHandler {
    private final AudioConverterService audioConverterService;
    private final TelegramUserRepository telegramUserRepository;
    private final ConvertedFileRepository convertedFileRepository;

    @Override
    public boolean support(Update update) {
        return update.hasMessage() && !update.getMessage().isCommand() && update.getMessage().hasText();
    }

    @Override
    @SneakyThrows
    public void handle(Update update, AudioConverterBot bot) {
        Message message = update.getMessage();
        String text = message.getText();

        try {
            SupportedFormat format = SupportedFormat.valueOf(text);
            TelegramUser telegramUser = telegramUserRepository.findOrCreateUser(message.getFrom(), message.getChatId());
            telegramUser.getAudioFile().setConvertingFormat(format);

            if (telegramUser.isReadyToConvert()) {
                TelegramHandler.tryConvert(message, telegramUser, bot, audioConverterService, convertedFileRepository);
            } else {
                bot.execute(new SendMessage(message.getChatId(), "З типом конвертації зрозуміло, а тепер надішли мені аудіо або голосове повідомлення."));
            }

            telegramUserRepository.save(telegramUser);
        } catch (IllegalArgumentException e) {
            bot.execute(new SendMessage(message.getChatId(), "На жаль, я поки не розумію тебе. Для початку відправ будь ласка аудіо або голосове повідомлення або формат в який мені потрібно конвертувати файл."));
        }
    }
}
