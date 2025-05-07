package com.example.userservice.domain.ports.out;

import com.example.userservice.domain.i18n.MessageKey;

public interface MessageProviderPort {
    String getMessage(MessageKey key, Object... args);
}
