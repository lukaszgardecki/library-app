package com.example.activityservice.infrastructure.http;

import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.values.*;
import com.example.activityservice.infrastructure.http.dto.ActivityDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ActivityMapperTest {

    @Test
    void shouldMapDtoToModel() {
        // given
        ActivityDto dto = ActivityDto.builder()
                .id(123L)
                .userId(456L)
                .type(ActivityType.LOGIN)
                .message("test message")
                .createdAt(LocalDateTime.of(2025, 5, 30, 12, 0))
                .build();

        // when
        Activity model = ActivityMapper.toModel(dto);

        // then
        assertThat(model.getId()).isEqualTo(new ActivityId(123L));
        assertThat(model.getUserId()).isEqualTo(new UserId(456L));
        assertThat(model.getType()).isEqualTo(ActivityType.LOGIN);
        assertThat(model.getMessage().value()).isEqualTo("test message");
        assertThat(model.getCreatedAt().value()).isEqualTo(LocalDateTime.of(2025, 5, 30, 12, 0));
    }

    @Test
    void shouldMapModelToDto() {
        // given
        Activity model = Activity.builder()
                .id(new ActivityId(123L))
                .userId(new UserId(456L))
                .type(ActivityType.LOGIN)
                .message(new ActivityMessage("test message"))
                .createdAt(new ActivityCreationDate(LocalDateTime.of(2025, 5, 30, 12, 0)))
                .build();

        // when
        ActivityDto dto = ActivityMapper.toDto(model);

        // then
        assertThat(dto.getId()).isEqualTo(123L);
        assertThat(dto.getUserId()).isEqualTo(456L);
        assertThat(dto.getType()).isEqualTo(ActivityType.LOGIN);
        assertThat(dto.getMessage()).isEqualTo("test message");
        assertThat(dto.getCreatedAt()).isEqualTo(LocalDateTime.of(2025, 5, 30, 12, 0));
    }

    @Test
    void shouldHandleNullIdWhenMappingModelToDto() {
        // given
        Activity model = Activity.builder()
                .id(null)
                .userId(new UserId(456L))
                .type(ActivityType.LOGIN)
                .message(new ActivityMessage("test message"))
                .createdAt(new ActivityCreationDate(LocalDateTime.of(2025, 5, 30, 12, 0)))
                .build();

        // when
        ActivityDto dto = ActivityMapper.toDto(model);

        // then
        assertThat(dto.getId()).isNull();
    }
}