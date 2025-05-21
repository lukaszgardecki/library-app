package com.example.warehouseservice.infrastructure.i18n;

import com.example.warehouseservice.domain.i18n.MessageKey;
import com.example.warehouseservice.domain.ports.out.MessageProviderPort;
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
