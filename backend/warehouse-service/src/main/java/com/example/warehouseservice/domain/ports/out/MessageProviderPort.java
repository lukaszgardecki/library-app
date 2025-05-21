package com.example.warehouseservice.domain.ports.out;

import com.example.warehouseservice.domain.i18n.MessageKey;

public interface MessageProviderPort {
    String getMessage(MessageKey key, Object... args);
}
