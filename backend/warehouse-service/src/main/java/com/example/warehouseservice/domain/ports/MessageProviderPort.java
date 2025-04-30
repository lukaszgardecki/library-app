package com.example.warehouseservice.domain.ports;

import com.example.warehouseservice.domain.MessageKey;

public interface MessageProviderPort {
    String getMessage(MessageKey key, Object... args);
}
