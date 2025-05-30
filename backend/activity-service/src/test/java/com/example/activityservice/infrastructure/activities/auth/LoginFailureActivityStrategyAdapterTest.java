package com.example.activityservice.infrastructure.activities.auth;

import com.example.activityservice.domain.i18n.MessageKey;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.values.ActivityType;
import com.example.activityservice.domain.model.values.UserId;
import com.example.activityservice.domain.ports.out.MessageProviderPort;
import com.example.activityservice.infrastructure.kafka.event.incoming.LoginFailureEvent;
import com.example.util.EventTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginFailureActivityStrategyAdapterTest {

    @Mock
    MessageProviderPort msgProvider;

    @InjectMocks
    LoginFailureActivityStrategyAdapter adapter;

    @Test
    void shouldCreateActivityFromLoginFailureEvent() {
        // given
        LoginFailureEvent event = EventTestFactory.createLoginFailureEvent();
        String expectedMessage = "testMsg";
        when(msgProvider.getMessage(
                MessageKey.ACTIVITY_LOGIN_FAILED, event.getFirstName(), event.getLastName()
        )).thenReturn(expectedMessage);

        // when
        Activity result = adapter.create(event);

        // then
        assertThat(result.getUserId()).isEqualTo(new UserId(event.getUserId()));
        assertThat(result.getType()).isEqualTo(ActivityType.LOGIN_FAILED);
        assertThat(result.getMessage().value()).isEqualTo(expectedMessage);
        verify(msgProvider).getMessage(MessageKey.ACTIVITY_LOGIN_FAILED, event.getFirstName(), event.getLastName());
    }

    @Test
    void shouldReturnCorrectSupportedType() {
        // when
        Class<LoginFailureEvent> supportedType = adapter.supports();

        // then
        assertThat(supportedType).isEqualTo(LoginFailureEvent.class);
    }
}