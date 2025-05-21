package com.example.loanservice.domain.ports.out;

import com.example.loanservice.domain.i18n.MessageKey;

public interface MessageProviderPort {
    String getMessage(MessageKey key, Object... args);
}
