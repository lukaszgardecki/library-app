package com.example.paymentservice.domain.ports.out;

import com.example.paymentservice.domain.i18n.MessageKey;

public interface MessageProviderPort {
    String getMessage(MessageKey key, Object... args);
}
