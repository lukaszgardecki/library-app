package com.example.libraryapp.application.message;

import com.example.libraryapp.domain.MessageKey;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

@RequiredArgsConstructor
class GetMessageUseCase {
    private final MessageSource messageSource;

    String execute(MessageKey messageKey, Object... params) {
        return messageSource.getMessage(messageKey.getKey(), params, LocaleContextHolder.getLocale());
    }
}
