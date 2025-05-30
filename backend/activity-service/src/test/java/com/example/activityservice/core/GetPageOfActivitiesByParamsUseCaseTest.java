package com.example.activityservice.core;

import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.values.UserId;
import com.example.activityservice.domain.ports.out.ActivityRepositoryPort;
import com.example.util.ActivityTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetPageOfActivitiesByParamsUseCaseTest {

    @Mock
    ActivityRepositoryPort activityRepository;

    @InjectMocks
    GetPageOfActivitiesByParamsUseCase getPageUseCase;

    @Test
    void shouldReturnPageOfActivitiesForGivenParams() {
        // given
        UserId userId = new UserId(1L);
        String type = "LOGIN";
        Pageable pageable = PageRequest.of(0, 10);
        Activity activity1 = ActivityTestFactory.activityWith(userId);
        Activity activity2 = ActivityTestFactory.activityWith(userId);
        Page<Activity> expectedPage = new PageImpl<>(List.of(activity1, activity2));
        when(activityRepository.findAllByParams(userId, type, pageable)).thenReturn(expectedPage);

        // when
        Page<Activity> result = getPageUseCase.execute(userId, type, pageable);

        // then
        assertThat(result.getContent()).containsExactly(activity1, activity2);
        verify(activityRepository).findAllByParams(userId, type, pageable);
        verifyNoMoreInteractions(activityRepository);
    }
}