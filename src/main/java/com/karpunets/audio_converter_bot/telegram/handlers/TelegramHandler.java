package com.karpunets.audio_converter_bot.telegram.handlers;

import com.google.common.base.Throwables;
import com.karpunets.audio_converter_bot.dao.ConvertedFileRepository;
import com.karpunets.audio_converter_bot.ffmpeg.SupportedFormat;
import com.karpunets.audio_converter_bot.model.ConvertedFile;
import com.karpunets.audio_converter_bot.model.TelegramUser;
import com.karpunets.audio_converter_bot.services.AudioConverterService;
import com.karpunets.audio_converter_bot.services.AudioStorageService;
import com.karpunets.audio_converter_bot.telegram.AudioConverterBot;
import com.karpunets.audio_converter_bot.telegram.services.TelegramEventProcessor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendAudio;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public interface TelegramHandler {

    boolean support(Update update);

    void handle(Update update, AudioConverterBot bot);

    @Autowired
    default void telegramEventProcessor(TelegramEventProcessor telegramEventProcessor) {
        telegramEventProcessor.registerHandler(this);
    }

    //private
    @SneakyThrows
    static void refreshUserAudioFile(String fileId, Message message, TelegramUser telegramUser, AudioConverterBot converterBot,
                                     AudioStorageService audioStorageService) {
        String fileUrl = converterBot.getFileUrl(fileId);

        converterBot.execute(new SendMessage()
                .setChatId(message.getChatId())
                .setText("Початок завантаження..."));

        File downloadedFile = audioStorageService.downloadFile(fileUrl);
        telegramUser.refreshAudioFile(fileId, downloadedFile);

        converterBot.execute(new SendMessage()
                .setChatId(message.getChatId())
                .setText("Я закінчив завантаження."));
    }

    //private
    @SneakyThrows
    static void sendFormatKeyboard(AudioConverterBot converterBot, Message message) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.getKeyboard().add(new KeyboardRow());

        SupportedFormat.names().forEach(text -> {
            List<KeyboardRow> keyboards = replyKeyboardMarkup.getKeyboard();
            KeyboardRow keyboardRow = keyboards.get(keyboards.size() - 1);
            if (keyboardRow.size() == 4) {
                keyboardRow = new KeyboardRow();
                keyboards.add(keyboardRow);
            }
            keyboardRow.add(text);
        });


        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setResizeKeyboard(true);

        converterBot.execute(new SendMessage()
                .setChatId(message.getChatId())
                .setText("В який формат потрібно конвертувати?")
                .setReplyMarkup(replyKeyboardMarkup));
    }

    //private
    @SneakyThrows
    static CompletableFuture<ConvertedFile> tryConvert(Message message, TelegramUser telegramUser,
                                                       AudioConverterBot converterBot,
                                                       AudioConverterService audioConverterService,
                                                       ConvertedFileRepository convertedFileRepository) {
        converterBot.execute(new SendMessage()
                .setChatId(message.getChatId())
                .setText("Початок конвертації..."));

        return audioConverterService
                .convert(telegramUser.getAudioFile())
                .thenApply(convertedFile -> {
                    try {
                        converterBot.sendAudio(new SendAudio()
                                .setChatId(message.getChatId())
                                .setTitle("converted" + convertedFile.getFormat().getFileFormat())
                                .setCaption("Дякую за користування. Будь які питання та пропозиції чекатиму на - arthur.karpunets@gmail.com.")
                                .setNewAudio(convertedFile.getFile()));
                    } catch (TelegramApiException e) {
                        Throwables.throwIfUnchecked(e);
                    }
                    return convertedFileRepository.save(convertedFile);
                }).exceptionally(throwable -> {
                    try {
                        converterBot.execute(new SendMessage()
                                .setChatId(message.getChatId())
                                .setText("Проблема конвертації. Ми вже працюємо над цією проблемою."));
                    } catch (TelegramApiException e) {
                        Throwables.throwIfUnchecked(e);
                    }
                    Throwables.throwIfUnchecked(throwable);
                    return null;
                });
    }
}
