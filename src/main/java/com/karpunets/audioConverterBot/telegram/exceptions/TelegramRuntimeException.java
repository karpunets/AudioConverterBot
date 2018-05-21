package com.karpunets.audioConverterBot.telegram.exceptions;

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
