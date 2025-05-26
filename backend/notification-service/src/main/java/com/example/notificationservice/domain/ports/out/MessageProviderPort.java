package com.example.notificationservice.domain.ports.out;

import com.example.notificationservice.domain.i18n.MessageKey;

public interface MessageProviderPort {
    String getMessage(MessageKey key, Object... args);
}
