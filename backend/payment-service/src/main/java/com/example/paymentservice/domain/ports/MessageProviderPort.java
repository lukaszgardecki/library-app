package com.example.paymentservice.domain.ports;

import com.example.paymentservice.domain.MessageKey;

public interface MessageProviderPort {
    String getMessage(MessageKey key, Object... args);
}
