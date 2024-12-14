package com.example.libraryapp.NEWapplication.message;

import com.example.libraryapp.NEWapplication.constants.Message;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MessageFacade {
    private final GetMessageUseCase getMessageUseCase;

    public String get(Message message) {
        return getMessageUseCase.execute(message);
    }
}
