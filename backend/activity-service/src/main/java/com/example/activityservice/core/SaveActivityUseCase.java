package com.example.activityservice.core;

import com.example.activityservice.domain.dto.UserActivityDto;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.ports.ActivityRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class SaveActivityUseCase {
    private final ActivityRepositoryPort userActivityRepository;

    Activity execute(UserActivityDto activity) {
        return userActivityRepository.save(UserActivityMapper.toModel(activity));
    }
}
