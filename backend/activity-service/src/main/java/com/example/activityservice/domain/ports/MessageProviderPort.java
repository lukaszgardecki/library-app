package com.example.activityservice.domain.ports;

import com.example.activityservice.domain.MessageKey;

public interface MessageProviderPort {
    String getMessage(MessageKey key, Object... args);
}
