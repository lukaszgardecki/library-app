package com.example.activityservice.infrastructure.activities.notification;

import com.example.activityservice.domain.i18n.MessageKey;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.values.ActivityType;
import com.example.activityservice.domain.model.values.UserId;
import com.example.activityservice.domain.ports.out.MessageProviderPort;
import com.example.activityservice.infrastructure.kafka.event.incoming.EmailNotificationSentEvent;
import com.example.util.EventTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailNotificationSentActivityStrategyAdapterTest {

    @Mock
    MessageProviderPort msgProvider;

    @InjectMocks
    EmailNotificationSentActivityStrategyAdapter adapter;

    @Test
    void shouldCreateActivityFromEmailNotificationSentEvent() {
        // given
        EmailNotificationSentEvent event = EventTestFactory.createEmailNotificationSentEvent();
        String expectedMessage = "testMsg";
        when(msgProvider.getMessage(
                MessageKey.ACTIVITY_NOTIFICATION_SENT_EMAIL,
                event.getNotificationSubject()
        )).thenReturn(expectedMessage);

        // when
        Activity activity = adapter.create(event);

        // then
        assertThat(activity.getUserId()).isEqualTo(new UserId(event.getUserId()));
        assertThat(activity.getType()).isEqualTo(ActivityType.NOTIFICATION_EMAIL);
        assertThat(activity.getMessage().value()).isEqualTo(expectedMessage);
        verify(msgProvider).getMessage(MessageKey.ACTIVITY_NOTIFICATION_SENT_EMAIL, event.getNotificationSubject());
        verifyNoMoreInteractions(msgProvider);
    }

    @Test
    void shouldSupportEmailNotificationSentEvent() {
        // when
        Class<EmailNotificationSentEvent> supportedClass = adapter.supports();

        // then
        assertThat(supportedClass).isEqualTo(EmailNotificationSentEvent.class);
    }
}
