package com.example.loanservice.domain.ports;

import com.example.loanservice.domain.MessageKey;

public interface MessageProviderPort {
    String getMessage(MessageKey key, Object... args);
}
