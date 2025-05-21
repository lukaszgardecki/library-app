package com.example.statisticsservice.domain.ports.out;

import com.example.statisticsservice.domain.i18n.MessageKey;

public interface MessageProviderPort {
    String getMessage(MessageKey key, Object... args);
}
