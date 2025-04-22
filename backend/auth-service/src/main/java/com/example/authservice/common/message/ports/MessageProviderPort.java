package com.example.authservice.common.message.ports;

import com.example.authservice.common.MessageKey;

public interface MessageProviderPort {
    String getMessage(MessageKey key, Object... args);
}
