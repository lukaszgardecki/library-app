package com.example.libraryapp.application.useractivity;

import com.example.libraryapp.domain.useractivity.model.UserActivity;
import com.example.libraryapp.domain.useractivity.ports.UserActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
class GetPageOfActivitiesByParamsUseCase {
    private final UserActivityRepository userActivityRepository;

    Page<UserActivity> execute(Long userId, String type, Pageable pageable) {
        return userActivityRepository.findAllByParams(userId, type, pageable);
    }
}
