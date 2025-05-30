package com.example.activityservice.infrastructure.activities.bookitem;

import com.example.activityservice.domain.i18n.MessageKey;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.values.ActivityType;
import com.example.activityservice.domain.model.values.UserId;
import com.example.activityservice.domain.ports.out.MessageProviderPort;
import com.example.activityservice.infrastructure.kafka.event.incoming.RequestCreatedEvent;
import com.example.util.EventTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequestCreatedActivityStrategyAdapterTest {

    @Mock
    MessageProviderPort msgProvider;

    @InjectMocks
    RequestCreatedActivityStrategyAdapter adapter;

    @Test
    void shouldCreateActivityFromRequestCreatedEvent() {
        // given
        RequestCreatedEvent event = EventTestFactory.createRequestCreatedEvent();
        String expectedMessage = "testMsg";
        when(msgProvider.getMessage(MessageKey.ACTIVITY_REQUEST_CREATED, event.getBookTitle()))
                .thenReturn(expectedMessage);

        // when
        Activity activity = adapter.create(event);

        // then
        assertThat(activity.getUserId()).isEqualTo(new UserId(event.getUserId()));
        assertThat(activity.getType()).isEqualTo(ActivityType.REQUEST_NEW);
        assertThat(activity.getMessage().value()).isEqualTo(expectedMessage);
        verify(msgProvider).getMessage(MessageKey.ACTIVITY_REQUEST_CREATED, event.getBookTitle());
        verifyNoMoreInteractions(msgProvider);
    }

    @Test
    void shouldSupportRequestCreatedEvent() {
        // when
        Class<RequestCreatedEvent> supportedClass = adapter.supports();

        // then
        assertThat(supportedClass).isEqualTo(RequestCreatedEvent.class);
    }
}
