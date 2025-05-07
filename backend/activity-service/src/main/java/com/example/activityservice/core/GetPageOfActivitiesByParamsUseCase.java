package com.example.activityservice.core;

import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.UserId;
import com.example.activityservice.domain.ports.ActivityRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
class GetPageOfActivitiesByParamsUseCase {
    private final ActivityRepositoryPort userActivityRepository;

    Page<Activity> execute(UserId userId, String type, Pageable pageable) {
        return userActivityRepository.findAllByParams(userId, type, pageable);
    }
}
