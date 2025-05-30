package com.example.activityservice.core;

import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.ports.out.ActivityRepositoryPort;
import com.example.util.ActivityTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaveActivityUseCaseTest {

    @Mock
    ActivityRepositoryPort activityRepository;

    @InjectMocks
    SaveActivityUseCase saveActivityUseCase;

    @Test
    void shouldSaveActivity() {
        // given
        Activity activity = ActivityTestFactory.defaultActivity();
        when(activityRepository.save(activity)).thenReturn(activity);

        // when
        Activity result = saveActivityUseCase.execute(activity);

        // then
        assertThat(result).isEqualTo(activity);
        verify(activityRepository).save(activity);
        verifyNoMoreInteractions(activityRepository);
    }
}