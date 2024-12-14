package com.example.libraryapp.NEWapplication.useractivity;

import com.example.libraryapp.NEWdomain.useractivity.dto.UserActivityDto;
import com.example.libraryapp.NEWdomain.useractivity.model.UserActivity;

class UserActivityMapper {

    static UserActivity toModel(UserActivityDto activity) {
        return UserActivity.builder()
                .id(activity.getId())
                .userId(activity.getUserId())
                .type(activity.getType())
                .message(activity.getMessage())
                .createdAt(activity.getCreatedAt())
                .build();
    }

    static UserActivityDto toDto(UserActivity userActivity) {
        return UserActivityDto.builder()
                .id(userActivity.getId())
                .userId(userActivity.getUserId())
                .type(userActivity.getType())
                .message(userActivity.getMessage())
                .createdAt(userActivity.getCreatedAt())
                .build();
    }
}
