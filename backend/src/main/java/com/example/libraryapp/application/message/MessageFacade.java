package com.example.libraryapp.application.message;

import com.example.libraryapp.domain.MessageKey;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MessageFacade {
    private final GetMessageUseCase getMessageUseCase;

    // TODO: 12.02.2025 ta fasada może się przydać przy dostarczaniu wiadomości do wyjątków

    public String get(MessageKey messageKey) {
        return getMessageUseCase.execute(messageKey);
    }
}
