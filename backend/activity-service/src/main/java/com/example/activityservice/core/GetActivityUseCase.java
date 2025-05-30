package com.example.activityservice.core;

import com.example.activityservice.domain.exception.UserActivityNotFoundException;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.values.ActivityId;
import com.example.activityservice.domain.ports.out.ActivityRepositoryPort;
import com.example.activityservice.domain.ports.out.SourceValidatorPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetActivityUseCase {
    private final ActivityRepositoryPort activityRepository;
    private final SourceValidatorPort sourceValidator;

    Activity execute(ActivityId id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new UserActivityNotFoundException(id));
        sourceValidator.validateUserIsOwner(activity.getUserId());
        return activity;
    }
}
