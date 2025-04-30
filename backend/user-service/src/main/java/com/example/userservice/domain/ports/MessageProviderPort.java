package com.example.userservice.domain.ports;

import com.example.userservice.domain.MessageKey;

public interface MessageProviderPort {
    String getMessage(MessageKey key, Object... args);
}
