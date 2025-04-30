package com.example.loanservice.infrastructure.i18n;

import com.example.loanservice.domain.MessageKey;
import com.example.loanservice.domain.ports.MessageProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class MessageProviderAdapter implements MessageProviderPort {
    private final MessageSource messageSource;

    @Override
    public String getMessage(MessageKey key, Object... args) {
        return messageSource.getMessage(key.getKey(), args, LocaleContextHolder.getLocale());
    }
}
