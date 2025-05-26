package com.example.fineservice.domain.ports.out;

import com.example.fineservice.domain.i18n.MessageKey;

public interface MessageProviderPort {
    String getMessage(MessageKey key, Object... args);
}
