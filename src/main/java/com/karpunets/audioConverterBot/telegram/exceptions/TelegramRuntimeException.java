package com.karpunets.audioConverterBot.telegram.exceptions;

/**
 * Created by Karpunets on 10.04.2018
 * Project: AudioConverterBot
 */
public class TelegramRuntimeException extends RuntimeException {
    TelegramRuntimeException() {
    }

    public TelegramRuntimeException(String message) {
        super(message);
    }

    public TelegramRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TelegramRuntimeException(Throwable cause) {
        super(cause);
    }
}
