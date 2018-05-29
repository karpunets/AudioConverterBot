package com.karpunets.audio_converter_bot.telegram.commands;

import com.karpunets.audio_converter_bot.dao.TelegramUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;

import javax.annotation.PostConstruct;


@Slf4j
@Service
@RequiredArgsConstructor
public class StartCommand implements BotCommand {
    private final TelegramUserRepository telegramUserRepository;

    @PostConstruct
    private void construct() {
        Registry.register(this);
    }

    @Override
    public String getCommandIdentifier() {
        return "/start";
    }

    @Override
    @SneakyThrows
    public void processMessage(AbsSender absSender, Message message) {
        User from = message.getFrom();
        telegramUserRepository.findOrCreateUser(from, message.getChatId());
        absSender.execute(new SendMessage(message.getChatId(), "Ласкаво просимо! Для початку конвертації достатньо завантажити файл або записати голосове повідомлення"));
    }
}
