package com.example.notificationservice.domain.ports;

import com.example.notificationservice.domain.MessageKey;

public interface MessageProviderPort {
    String getMessage(MessageKey key, Object... args);
}
