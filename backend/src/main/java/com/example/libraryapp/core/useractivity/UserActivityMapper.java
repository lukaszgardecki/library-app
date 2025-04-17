package com.example.libraryapp.core.useractivity;

import com.example.libraryapp.domain.user.model.UserId;
import com.example.libraryapp.domain.useractivity.dto.UserActivityDto;
import com.example.libraryapp.domain.useractivity.model.UserActivity;
import com.example.libraryapp.domain.useractivity.model.UserActivityCreationDate;
import com.example.libraryapp.domain.useractivity.model.UserActivityId;
import com.example.libraryapp.domain.useractivity.model.UserActivityMessage;

class UserActivityMapper {

    static UserActivity toModel(UserActivityDto activity) {
        return UserActivity.builder()
                .id(new UserActivityId(activity.getId()))
                .userId(new UserId(activity.getUserId()))
                .type(activity.getType())
                .message(new UserActivityMessage(activity.getMessage()))
                .createdAt(new UserActivityCreationDate(activity.getCreatedAt()))
                .build();
    }

    static UserActivityDto toDto(UserActivity userActivity) {
        return UserActivityDto.builder()
                .id(userActivity.getId() != null ? userActivity.getId().value() : null)
                .userId(userActivity.getUserId().value())
                .type(userActivity.getType())
                .message(userActivity.getMessage().value())
                .createdAt(userActivity.getCreatedAt().value())
                .build();
    }
}
