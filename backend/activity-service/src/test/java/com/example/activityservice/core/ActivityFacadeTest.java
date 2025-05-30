package com.example.activityservice.core;

import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.values.ActivityId;
import com.example.activityservice.domain.model.values.UserId;
import com.example.util.ActivityTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivityFacadeTest {

    @Mock
    GetPageOfActivitiesByParamsUseCase getPageOfActivitiesByParamsUseCase;

    @Mock
    GetActivityUseCase getActivityUseCase;

    @Mock
    SaveActivityUseCase saveActivityUseCase;

    @InjectMocks
    ActivityFacade activityFacade;

    @Test
    void getPageOfActivities() {
        // given
        PageRequest pageable = PageRequest.of(0, 10);
        UserId userId = new UserId(1L);
        Page<Activity> expectedPage = new PageImpl<>(List.of(
                ActivityTestFactory.activityWith(userId),
                ActivityTestFactory.activityWith(userId)
        ));
        when(getPageOfActivitiesByParamsUseCase.execute(userId, "LOGIN", pageable))
                .thenReturn(expectedPage);

        // when
        Page<Activity> result = activityFacade.getPageOfActivities(userId, "LOGIN", pageable);

        // then
        assertThat(result).isSameAs(expectedPage);
        verify(getPageOfActivitiesByParamsUseCase).execute(userId, "LOGIN", pageable);
        verifyNoMoreInteractions(getPageOfActivitiesByParamsUseCase);
    }

    @Test
    void getActivity() {
        // given
        ActivityId activityId = new ActivityId(1L);
        Activity expectedActivity = ActivityTestFactory.activityWith(activityId);
        when(getActivityUseCase.execute(activityId)).thenReturn(expectedActivity);

        // when
        Activity result = activityFacade.getActivity(activityId);

        // then
        assertThat(result).isSameAs(expectedActivity);
        verify(getActivityUseCase).execute(activityId);
        verifyNoMoreInteractions(getActivityUseCase);
    }

    @Test
    void saveActivity() {
        // given
        Activity expectedActivity = ActivityTestFactory.defaultActivity();
        when(saveActivityUseCase.execute(expectedActivity)).thenReturn(expectedActivity);

        // when
        Activity result = activityFacade.saveActivity(expectedActivity);

        // then
        assertThat(result).isSameAs(expectedActivity);
        verify(saveActivityUseCase).execute(expectedActivity);
        verifyNoMoreInteractions(saveActivityUseCase);
    }
}