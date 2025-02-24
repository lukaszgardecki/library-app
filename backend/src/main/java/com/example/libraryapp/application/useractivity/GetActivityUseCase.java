package com.example.libraryapp.application.useractivity;

import com.example.libraryapp.domain.useractivity.exceptions.UserActivityNotFoundException;
import com.example.libraryapp.domain.useractivity.model.UserActivity;
import com.example.libraryapp.domain.useractivity.ports.UserActivityRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetActivityUseCase {
    private final UserActivityRepositoryPort userActivityRepository;

    UserActivity execute(Long id) {
        return userActivityRepository.findById(id)
                .orElseThrow(() -> new UserActivityNotFoundException(id));
    }
}
