package com.example.fineservice.domain.ports;

import com.example.fineservice.domain.MessageKey;

public interface MessageProviderPort {
    String getMessage(MessageKey key, Object... args);
}
