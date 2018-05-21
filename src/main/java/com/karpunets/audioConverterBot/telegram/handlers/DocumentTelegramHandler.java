package com.karpunets.audioConverterBot.telegram.handlers;

import com.karpunets.audioConverterBot.telegram.AudioConverterBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.Document;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.AbsSender;

@Service
@Slf4j
public class DocumentTelegramHandler implements TelegramHandler {

    @Override
    public boolean support(Update update) {
        return update.hasMessage() && update.getMessage().hasDocument();
    }

    @Override
    public void handle(Update update, AudioConverterBot converterBot) {
        Document document = update.getMessage().getDocument();
        log.info("Get document fileId={} mimeType={}", document.getFileId(), document.getMimeType());

    }
}
