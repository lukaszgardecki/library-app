package com.example.libraryapp.core.useractivity;

import com.example.libraryapp.domain.useractivity.dto.UserActivityDto;
import com.example.libraryapp.domain.useractivity.model.UserActivity;
import com.example.libraryapp.domain.useractivity.ports.UserActivityRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class SaveActivityUseCase {
    private final UserActivityRepositoryPort userActivityRepository;

    UserActivity execute(UserActivityDto activity) {
        return userActivityRepository.save(UserActivityMapper.toModel(activity));
    }
}
