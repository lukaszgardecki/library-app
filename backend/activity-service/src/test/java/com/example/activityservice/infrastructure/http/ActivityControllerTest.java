package com.example.activityservice.infrastructure.http;

import com.example.activityservice.core.ActivityFacade;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.values.ActivityId;
import com.example.activityservice.domain.model.values.UserId;
import com.example.activityservice.infrastructure.http.dto.ActivityDto;
import com.example.util.ActivityTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActivityControllerTest {

    @Mock
    private ActivityFacade activityFacade;

    @InjectMocks
    private ActivityController controller;

    @Test
    void shouldReturnPageOfActivities() {
        // given
        ActivityDto dto = ActivityMapper.toDto(
                ActivityTestFactory.activityWith(new ActivityId(1L), new UserId(2L))
        );
        Page<Activity> page = new PageImpl<>(List.of(ActivityMapper.toModel(dto)));
        when(activityFacade.getPageOfActivities(any(), any(), any())).thenReturn(page);

        // when
        ResponseEntity<Page<ActivityDto>> result = controller.getAllActions(2L, "LOGIN", PageRequest.of(0, 10));

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getContent()).hasSize(1);
        assertThat(result.getBody().getContent().get(0).getId()).isEqualTo(1L);
        assertThat(result.getBody().getContent().get(0).getUserId()).isEqualTo(2L);
    }

    @Test
    void shouldReturnSingleActivity() {
        // given
        ActivityDto dto = ActivityMapper.toDto(
                ActivityTestFactory.activityWith(new ActivityId(1L), new UserId(2L))
        );
        when(activityFacade.getActivity(new ActivityId(1L))).thenReturn(ActivityMapper.toModel(dto));

        // when
        ResponseEntity<ActivityDto> result = controller.getActionById(1L);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(1L);
        assertThat(result.getBody().getUserId()).isEqualTo(2L);
    }
}