package com.example.activityservice.domain.ports.out;

import com.example.activityservice.domain.i18n.MessageKey;

public interface MessageProviderPort {
    String getMessage(MessageKey key, Object... args);
}
