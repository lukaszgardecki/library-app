package com.example.authservice.domain.ports.out;

import com.example.authservice.domain.i18n.MessageKey;

public interface MessageProviderPort {
    String getMessage(MessageKey key, Object... args);
}
