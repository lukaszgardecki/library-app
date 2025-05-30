package com.example.activityservice.infrastructure.activities.bookitem;

import com.example.activityservice.domain.i18n.MessageKey;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.values.ActivityType;
import com.example.activityservice.domain.model.values.UserId;
import com.example.activityservice.domain.ports.out.MessageProviderPort;
import com.example.activityservice.infrastructure.kafka.event.incoming.RequestReadyEvent;
import com.example.util.EventTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequestReadyActivityStrategyAdapterTest {

    @Mock
    MessageProviderPort msgProvider;

    @InjectMocks
    RequestReadyActivityStrategyAdapter adapter;

    @Test
    void shouldCreateActivityFromRequestReadyEvent() {
        // given
        RequestReadyEvent event = EventTestFactory.createRequestReadyEvent();
        String expectedMessage = "testMsg";
        when(msgProvider.getMessage(MessageKey.ACTIVITY_REQUEST_COMPLETED, event.getBookTitle()))
                .thenReturn(expectedMessage);

        // when
        Activity activity = adapter.create(event);

        // then
        assertThat(activity.getUserId()).isEqualTo(new UserId(event.getUserId()));
        assertThat(activity.getType()).isEqualTo(ActivityType.REQUEST_COMPLETED);
        assertThat(activity.getMessage().value()).isEqualTo(expectedMessage);
        verify(msgProvider).getMessage(MessageKey.ACTIVITY_REQUEST_COMPLETED, event.getBookTitle());
        verifyNoMoreInteractions(msgProvider);
    }

    @Test
    void shouldSupportRequestReadyEvent() {
        // when
        Class<RequestReadyEvent> supportedClass = adapter.supports();

        // then
        assertThat(supportedClass).isEqualTo(RequestReadyEvent.class);
    }
}
