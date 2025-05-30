package com.example.activityservice.infrastructure.i18n;

import com.example.activityservice.domain.i18n.MessageKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageProviderAdapterTest {

    @Mock
    private MessageSource messageSource;

    private MessageProviderAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new MessageProviderAdapter(messageSource);
    }

    @Test
    void shouldDelegateMessageToMessageSourceWithLocale() {
        // given
        MessageKey key = MessageKey.ACTIVITY_BOOK_ITEM_LOST;
        Object[] args = {"arg1", "arg2"};
        String expectedMessage = "Translated message";
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        when(messageSource.getMessage(key.getKey(), args, LocaleContextHolder.getLocale()))
                .thenReturn(expectedMessage);

        // when
        String result = adapter.getMessage(key, args);

        // then
        assertThat(result).isEqualTo(expectedMessage);
        verify(messageSource).getMessage(key.getKey(), args, LocaleContextHolder.getLocale());
    }
}
