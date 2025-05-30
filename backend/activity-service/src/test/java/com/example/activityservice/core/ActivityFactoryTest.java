package com.example.activityservice.core;

import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.ports.in.ActivityCreationStrategyPort;
import com.example.activityservice.infrastructure.kafka.event.incoming.LoginSuccessEvent;
import com.example.util.ActivityTestFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class ActivityFactoryTest {

    @Test
    void shouldCreateActivityUsingCorrectStrategy() {
        // given
        LoginSuccessEvent event = new LoginSuccessEvent();
        Activity expectedActivity = ActivityTestFactory.defaultActivity();

        @SuppressWarnings("unchecked")
        ActivityCreationStrategyPort<LoginSuccessEvent> strategy = mock(ActivityCreationStrategyPort.class);
        when(strategy.supports()).thenReturn(LoginSuccessEvent.class);
        when(strategy.create(event)).thenReturn(expectedActivity);

        ActivityFactory factory = new ActivityFactory(List.of(strategy));

        // when
        Activity result = factory.createActivity(event);

        // then
        assertThat(result).isEqualTo(expectedActivity);
        verify(strategy).create(event);
    }

    @Test
    void shouldThrowExceptionWhenNoStrategyFound() {
        // given
        ActivityFactory factory = new ActivityFactory(List.of());
        Object unknownEvent = new Object();

        // when + then
        assertThatThrownBy(() -> factory.createActivity(unknownEvent))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No strategy found for event type");
    }
}