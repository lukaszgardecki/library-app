package com.example.authservice.domain.ports;

import com.example.authservice.domain.MessageKey;

public interface MessageProviderPort {
    String getMessage(MessageKey key, Object... args);
}
