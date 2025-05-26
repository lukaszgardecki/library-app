package com.example.catalogservice.domain.ports.out;

import com.example.catalogservice.domain.i18n.MessageKey;

public interface MessageProviderPort {
    String getMessage(MessageKey key, Object... args);
}
