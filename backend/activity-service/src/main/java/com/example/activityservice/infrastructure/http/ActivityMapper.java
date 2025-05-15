package com.example.activityservice.infrastructure.http;

import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.values.ActivityCreationDate;
import com.example.activityservice.domain.model.values.ActivityId;
import com.example.activityservice.domain.model.values.ActivityMessage;
import com.example.activityservice.domain.model.values.UserId;
import com.example.activityservice.infrastructure.http.dto.ActivityDto;

class ActivityMapper {

    static Activity toModel(ActivityDto activity) {
        return Activity.builder()
                .id(new ActivityId(activity.getId()))
                .userId(new UserId(activity.getUserId()))
                .type(activity.getType())
                .message(new ActivityMessage(activity.getMessage()))
                .createdAt(new ActivityCreationDate(activity.getCreatedAt()))
                .build();
    }

    static ActivityDto toDto(Activity activity) {
        return ActivityDto.builder()
                .id(activity.getId() != null ? activity.getId().value() : null)
                .userId(activity.getUserId().value())
                .type(activity.getType())
                .message(activity.getMessage().value())
                .createdAt(activity.getCreatedAt().value())
                .build();
    }
}
