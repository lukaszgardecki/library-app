package com.example.requestservice.domain.ports.out;

import com.example.requestservice.domain.i18n.MessageKey;

public interface MessageProviderPort {
    String getMessage(MessageKey key, Object... args);
}
