package com.karpunets.audioConverterBot.telegram.handlers;

import com.karpunets.audioConverterBot.database.dao.TelegramUserRepository;
import com.karpunets.audioConverterBot.database.model.AudioFile;
import com.karpunets.audioConverterBot.database.model.TelegramUser;
import com.karpunets.audioConverterBot.services.AudioConverterService;
import com.karpunets.audioConverterBot.telegram.AudioConverterBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.Voice;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Service
@Slf4j
@RequiredArgsConstructor
public class VoiceTelegramHandler implements TelegramHandler {
    private final AudioConverterService audioConverterService;
    private final TelegramUserRepository telegramUserRepository;

    @Override
    public boolean support(Update update) {
        return update.hasMessage() && update.getMessage().getVoice() != null;
    }

    @Override
    public void handle(Update update, AudioConverterBot converterBot) {
        Message message = update.getMessage();
        Voice voice = message.getVoice();

        KeyboardRow keyboardRow = new KeyboardRow();
        audioConverterService.getSupportedType().forEach(keyboardRow::add);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.getKeyboard().add(keyboardRow);

        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setResizeKeyboard(true);

        SendMessage sendMessage = new SendMessage(message.getChatId(), "Choose type")
                .setReplyMarkup(replyKeyboardMarkup);

        try {
            converterBot.execute(sendMessage);
            User from = message.getFrom();
            TelegramUser telegramUser = telegramUserRepository.findOrCreateUser(from);
            telegramUser.setAudioFile(AudioFile.of(message, converterBot.getFileUrl(voice.getFileId())));
            telegramUserRepository.save(telegramUser);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
