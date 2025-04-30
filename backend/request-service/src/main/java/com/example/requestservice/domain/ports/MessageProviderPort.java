package com.example.requestservice.domain.ports;

import com.example.requestservice.domain.MessageKey;

public interface MessageProviderPort {
    String getMessage(MessageKey key, Object... args);
}
