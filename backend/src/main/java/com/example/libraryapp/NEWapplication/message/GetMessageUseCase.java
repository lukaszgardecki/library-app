package com.example.libraryapp.NEWapplication.message;

import com.example.libraryapp.NEWapplication.constants.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

@RequiredArgsConstructor
class GetMessageUseCase {
    private final MessageSource messageSource;

    String execute(Message message, Object... params) {
        return messageSource.getMessage(message.getKey(), params, LocaleContextHolder.getLocale());
    }
}
