package com.example.activityservice.core;

import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.ports.out.ActivityRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class SaveActivityUseCase {
    private final ActivityRepositoryPort userActivityRepository;

    Activity execute(Activity activity) {
        return userActivityRepository.save(activity);
    }
}
