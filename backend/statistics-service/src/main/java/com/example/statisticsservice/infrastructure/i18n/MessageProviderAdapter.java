package com.example.statisticsservice.infrastructure.i18n;

import com.example.statisticsservice.domain.i18n.MessageKey;
import com.example.statisticsservice.domain.ports.out.MessageProviderPort;
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
