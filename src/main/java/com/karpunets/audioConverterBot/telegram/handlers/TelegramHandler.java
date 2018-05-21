package com.karpunets.audioConverterBot.telegram.handlers;

import com.karpunets.audioConverterBot.telegram.AudioConverterBot;
import com.karpunets.audioConverterBot.telegram.services.TelegramEventProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.objects.File;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Service
public interface TelegramHandler {

    boolean support(Update update);

    void handle(Update update, AudioConverterBot converterBot);

    @Autowired
    default void telegramEventProcessor(TelegramEventProcessor telegramEventProcessor) {
        telegramEventProcessor.registerHandler(this);
    }

    default String getFilePath(String fileId, AbsSender absSender) {
        GetFile getFileMethod = new GetFile();
        getFileMethod.setFileId(fileId);
        try {
            File file = absSender.execute(getFileMethod);
            return file.getFilePath();
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
