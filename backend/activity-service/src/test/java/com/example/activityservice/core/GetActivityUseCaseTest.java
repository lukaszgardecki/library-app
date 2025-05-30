package com.example.activityservice.core;

import com.example.activityservice.domain.exception.UserActivityNotFoundException;
import com.example.activityservice.domain.i18n.MessageKey;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.values.ActivityId;
import com.example.activityservice.domain.model.values.UserId;
import com.example.activityservice.domain.ports.out.ActivityRepositoryPort;
import com.example.activityservice.domain.ports.out.SourceValidatorPort;
import com.example.util.ActivityTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetActivityUseCaseTest {

    @Mock
    ActivityRepositoryPort activityRepository;

    @Mock
    SourceValidatorPort sourceValidator;

    @InjectMocks
    GetActivityUseCase getActivityUseCase;

    @Test
    void shouldReturnActivityWhenFoundAndUserIsOwner() {
        // given
        ActivityId id = new ActivityId(1L);
        UserId userId = new UserId(2L);
        Activity expectedActivity = ActivityTestFactory.activityWith(id, userId);
        when(activityRepository.findById(id)).thenReturn(Optional.of(expectedActivity));

        // when
        Activity result = getActivityUseCase.execute(id);

        // then
        assertThat(result).isEqualTo(expectedActivity);
        verify(activityRepository).findById(id);
        verify(sourceValidator).validateUserIsOwner(userId);
        verifyNoMoreInteractions(activityRepository, sourceValidator);
    }

    @Test
    void shouldThrowExceptionWhenActivityNotFound() {
        // given
        ActivityId id = new ActivityId(999L);
        when(activityRepository.findById(id)).thenReturn(Optional.empty());

        // when / then
        assertThatThrownBy(() -> getActivityUseCase.execute(id))
                .isInstanceOf(UserActivityNotFoundException.class)
                .hasMessageContaining(MessageKey.ACTIVITY_NOT_FOUND_ID.name());

        verify(activityRepository).findById(id);
        verifyNoMoreInteractions(activityRepository, sourceValidator);
    }
}