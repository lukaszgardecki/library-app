package com.example.activityservice.core;

import com.example.activityservice.domain.dto.UserActivityDto;
import com.example.activityservice.domain.model.*;

class UserActivityMapper {

    static Activity toModel(UserActivityDto activity) {
        return Activity.builder()
                .id(new ActivityId(activity.getId()))
                .userId(new UserId(activity.getUserId()))
                .type(activity.getType())
                .message(new ActivityMessage(activity.getMessage()))
                .createdAt(new ActivityCreationDate(activity.getCreatedAt()))
                .build();
    }

    static UserActivityDto toDto(Activity activity) {
        return UserActivityDto.builder()
                .id(activity.getId() != null ? activity.getId().value() : null)
                .userId(activity.getUserId().value())
                .type(activity.getType())
                .message(activity.getMessage().value())
                .createdAt(activity.getCreatedAt().value())
                .build();
    }
}
