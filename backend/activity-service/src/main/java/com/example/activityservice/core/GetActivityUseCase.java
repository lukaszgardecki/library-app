package com.example.activityservice.core;

import com.example.activityservice.domain.exception.UserActivityNotFoundException;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.ActivityId;
import com.example.activityservice.domain.ports.ActivityRepositoryPort;
import com.example.activityservice.domain.ports.SourceValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetActivityUseCase {
    private final ActivityRepositoryPort userActivityRepository;
    private final SourceValidator sourceValidator;

    Activity execute(ActivityId id) {
        Activity activity = userActivityRepository.findById(id)
                .orElseThrow(() -> new UserActivityNotFoundException(id));
        sourceValidator.validateUserIsOwner(activity.getUserId());
        return activity;
    }
}
