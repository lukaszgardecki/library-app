package com.example.libraryapp.application.useractivity;

import com.example.libraryapp.domain.useractivity.dto.UserActivityDto;
import com.example.libraryapp.domain.useractivity.model.UserActivity;
import com.example.libraryapp.domain.useractivity.ports.UserActivityRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class SaveActivityUseCase {
    private final UserActivityRepository userActivityRepository;

    UserActivity execute(UserActivityDto activity) {
        return userActivityRepository.save(UserActivityMapper.toModel(activity));
    }
}
