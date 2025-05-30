package com.example.activityservice.core;

import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.ports.out.ActivityRepositoryPort;
import com.example.util.ActivityTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventListenerServiceTest {

    @Mock
    ActivityRepositoryPort activityRepository;

    @Mock
    ActivityFactory activityFactory;

    @InjectMocks
    EventListenerService eventListenerService;

    @Test
    void shouldHandleEventByCreatingAndSavingActivity() {
        // given
        Object event = new Object();
        Activity activity = ActivityTestFactory.defaultActivity();
        when(activityFactory.createActivity(event)).thenReturn(activity);

        // when
        eventListenerService.handle(event);

        // then
        verify(activityFactory).createActivity(event);
        verify(activityRepository).save(activity);
        verifyNoMoreInteractions(activityFactory, activityRepository);
    }
}