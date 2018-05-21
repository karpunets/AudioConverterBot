package com.karpunets.audioConverterBot.telegram;

import com.karpunets.audioConverterBot.telegram.services.TelegramEventProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

@Slf4j
@RequiredArgsConstructor
@Component
public class AudioConverterBot extends TelegramLongPollingBot {
    private final TelegramEventProcessor eventProcessor;
    private final TelegramProperties properties;

    @Autowired
    private void telegramBotsApi(TelegramBotsApi telegramBotsApi) {
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiRequestException e) {
            log.error("Error when register", e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        eventProcessor.process(update, this);
    }

    @Override
    public String getBotUsername() {
        return properties.getBot().getUsername();
    }

    @Override
    public String getBotToken() {
        return properties.getBot().getToken();
    }

    public String getFileUrl(String fileId) throws TelegramApiException {
        GetFile getFile = new GetFile().setFileId(fileId);
        return execute(getFile).getFileUrl(properties.getBot().getToken());
//        FileUtils.copyURLToFile(fileUrl, File)
    }
}
