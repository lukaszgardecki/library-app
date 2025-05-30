package com.example.activityservice.infrastructure.activities.auth;

import com.example.activityservice.domain.i18n.MessageKey;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.values.ActivityType;
import com.example.activityservice.domain.model.values.UserId;
import com.example.activityservice.domain.ports.out.MessageProviderPort;
import com.example.activityservice.infrastructure.kafka.event.incoming.UserCreatedEvent;
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
class UserCreatedActivityStrategyAdapterTest {

    @Mock
    MessageProviderPort msgProvider;

    @InjectMocks
    UserCreatedActivityStrategyAdapter adapter;

    @Test
    void shouldCreateActivityFromUserCreatedEvent() {
        // given
        UserCreatedEvent event = EventTestFactory.createUserCreatedEvent();
        String expectedMessage = "testMsg";
        when(msgProvider.getMessage(
                MessageKey.ACTIVITY_REGISTER, event.getFirstName(), event.getLastName()
        )).thenReturn(expectedMessage);

        // when
        Activity activity = adapter.create(event);

        // then
        assertThat(activity.getUserId()).isEqualTo(new UserId(event.getUserId()));
        assertThat(activity.getType()).isEqualTo(ActivityType.REGISTER);
        assertThat(activity.getMessage().value()).isEqualTo(expectedMessage);
        verify(msgProvider).getMessage(MessageKey.ACTIVITY_REGISTER, event.getFirstName(), event.getLastName());
    }

    @Test
    void shouldSupportUserCreatedEvent() {
        // when
        Class<UserCreatedEvent> supportedClass = adapter.supports();

        // then
        assertThat(supportedClass).isEqualTo(UserCreatedEvent.class);
    }
}