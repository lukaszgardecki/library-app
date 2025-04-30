package com.example.catalogservice.domain.ports;

import com.example.catalogservice.domain.MessageKey;

public interface MessageProviderPort {
    String getMessage(MessageKey key, Object... args);
}
