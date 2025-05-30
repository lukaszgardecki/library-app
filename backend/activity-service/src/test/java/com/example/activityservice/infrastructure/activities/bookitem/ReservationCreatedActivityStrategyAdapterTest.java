package com.example.activityservice.infrastructure.activities.bookitem;

import com.example.activityservice.domain.i18n.MessageKey;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.values.ActivityType;
import com.example.activityservice.domain.model.values.UserId;
import com.example.activityservice.domain.ports.out.MessageProviderPort;
import com.example.activityservice.infrastructure.kafka.event.incoming.ReservationCreatedEvent;
import com.example.util.EventTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationCreatedActivityStrategyAdapterTest {

    @Mock
    MessageProviderPort msgProvider;

    @InjectMocks
    ReservationCreatedActivityStrategyAdapter adapter;

    @Test
    void shouldCreateActivityForFirstPersonInQueue() {
        // given
        ReservationCreatedEvent event = EventTestFactory.createReservationCreatedEvent(1);
        String expectedMessage = "testMsg";
        when(msgProvider.getMessage(
                MessageKey.ACTIVITY_BOOK_ITEM_RESERVED_FIRST_PERSON,
                event.getLoanDueDate(),
                event.getBookTitle())
        ).thenReturn(expectedMessage);

        // when
        Activity activity = adapter.create(event);

        // then
        assertThat(activity.getUserId()).isEqualTo(new UserId(event.getUserId()));
        assertThat(activity.getType()).isEqualTo(ActivityType.BOOK_RESERVED_FIRST);
        assertThat(activity.getMessage().value()).isEqualTo(expectedMessage);
        verify(msgProvider).getMessage(
                MessageKey.ACTIVITY_BOOK_ITEM_RESERVED_FIRST_PERSON,
                event.getLoanDueDate(),
                event.getBookTitle()
        );
        verifyNoMoreInteractions(msgProvider);
    }

    @Test
    void shouldCreateActivityForOtherPersonInQueue() {
        // given
        ReservationCreatedEvent event = EventTestFactory.createReservationCreatedEvent(3);
        String expectedMessage = "testMsg";
        when(msgProvider.getMessage(
                MessageKey.ACTIVITY_BOOK_ITEM_RESERVED_QUEUE,
                event.getBookTitle(),
                event.getQueue())
        ).thenReturn(expectedMessage);

        // when
        Activity activity = adapter.create(event);

        // then
        assertThat(activity.getUserId()).isEqualTo(new UserId(event.getUserId()));
        assertThat(activity.getType()).isEqualTo(ActivityType.BOOK_RESERVED_QUEUE);
        assertThat(activity.getMessage().value()).isEqualTo(expectedMessage);
        verify(msgProvider).getMessage(
                MessageKey.ACTIVITY_BOOK_ITEM_RESERVED_QUEUE,
                event.getBookTitle(),
                event.getQueue()
        );
        verifyNoMoreInteractions(msgProvider);
    }

    @Test
    void shouldSupportReservationCreatedEvent() {
        // when
        Class<ReservationCreatedEvent> supportedClass = adapter.supports();

        // then
        assertThat(supportedClass).isEqualTo(ReservationCreatedEvent.class);
    }
}
