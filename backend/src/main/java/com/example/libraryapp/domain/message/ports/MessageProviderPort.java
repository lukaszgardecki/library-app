package com.example.libraryapp.domain.message.ports;

import com.example.libraryapp.domain.MessageKey;

public interface MessageProviderPort {
    String getMessage(MessageKey key, Object... args);
}
