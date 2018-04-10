package com.karpunets.audioConverterBot.telegram.exceptions;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.telegram.telegrambots.api.methods.BotApiMethod;

import java.io.Serializable;

/**
 * Created by Karpunets on 10.04.2018
 * Project: AudioConverterBot
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class TelegramHandleUpdateException extends TelegramRuntimeException {
    private final BotApiMethod<? extends Serializable> answer;
}