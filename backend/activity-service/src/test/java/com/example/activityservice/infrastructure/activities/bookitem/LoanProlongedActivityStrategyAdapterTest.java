package com.example.activityservice.infrastructure.activities.bookitem;

import com.example.activityservice.domain.i18n.MessageKey;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.values.ActivityType;
import com.example.activityservice.domain.model.values.UserId;
import com.example.activityservice.domain.ports.out.MessageProviderPort;
import com.example.activityservice.infrastructure.kafka.event.incoming.LoanProlongedEvent;
import com.example.util.EventTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanProlongedActivityStrategyAdapterTest {

    @Mock
    MessageProviderPort msgProvider;

    @InjectMocks
    LoanProlongedActivityStrategyAdapter adapter;

    @Test
    void shouldCreateActivityFromLoanProlongedEvent() {
        // given
        LoanProlongedEvent event = EventTestFactory.createLoanProlongedEvent();
        String expectedMessage = "testMsg";
        when(msgProvider.getMessage(MessageKey.ACTIVITY_BOOK_ITEM_RENEWED, event.getBookTitle()))
                .thenReturn(expectedMessage);

        // when
        Activity activity = adapter.create(event);

        // then
        assertThat(activity.getUserId()).isEqualTo(new UserId(event.getUserId()));
        assertThat(activity.getType()).isEqualTo(ActivityType.BOOK_RENEWED);
        assertThat(activity.getMessage().value()).isEqualTo(expectedMessage);
        verify(msgProvider).getMessage(MessageKey.ACTIVITY_BOOK_ITEM_RENEWED, event.getBookTitle());
        verifyNoMoreInteractions(msgProvider);
    }

    @Test
    void shouldSupportLoanProlongedEvent() {
        // when
        Class<LoanProlongedEvent> supportedClass = adapter.supports();

        // then
        assertThat(supportedClass).isEqualTo(LoanProlongedEvent.class);
    }
}
