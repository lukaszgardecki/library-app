package com.example.libraryapp.application.useractivity;

import com.example.libraryapp.domain.useractivity.model.UserActivity;
import com.example.libraryapp.domain.useractivity.ports.UserActivityRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
class GetPageOfActivitiesByParamsUseCase {
    private final UserActivityRepositoryPort userActivityRepository;

    Page<UserActivity> execute(Long userId, String type, Pageable pageable) {
        return userActivityRepository.findAllByParams(userId, type, pageable);
    }
}
