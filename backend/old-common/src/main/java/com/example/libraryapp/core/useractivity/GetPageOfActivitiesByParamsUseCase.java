package com.example.libraryapp.core.useractivity;

import com.example.libraryapp.domain.user.model.UserId;
import com.example.libraryapp.domain.useractivity.model.UserActivity;
import com.example.libraryapp.domain.useractivity.ports.UserActivityRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
class GetPageOfActivitiesByParamsUseCase {
    private final UserActivityRepositoryPort userActivityRepository;

    Page<UserActivity> execute(UserId userId, String type, Pageable pageable) {
        return userActivityRepository.findAllByParams(userId, type, pageable);
    }
}
