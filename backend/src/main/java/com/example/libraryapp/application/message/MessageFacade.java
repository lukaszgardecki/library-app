package com.example.libraryapp.application.message;

import com.example.libraryapp.application.constants.Message;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MessageFacade {
    private final GetMessageUseCase getMessageUseCase;

    // TODO: 12.02.2025 ta fasada może się przydać przy dostarczaniu wiadomości do wyjątków

    public String get(Message message) {
        return getMessageUseCase.execute(message);
    }
}
