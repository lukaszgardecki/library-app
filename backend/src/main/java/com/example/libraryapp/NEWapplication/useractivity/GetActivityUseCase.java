package com.example.libraryapp.NEWapplication.useractivity;

import com.example.libraryapp.NEWdomain.useractivity.exceptions.UserActivityNotFoundException;
import com.example.libraryapp.NEWdomain.useractivity.model.UserActivity;
import com.example.libraryapp.NEWdomain.useractivity.ports.UserActivityRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetActivityUseCase {
    private final UserActivityRepository userActivityRepository;

    UserActivity execute(Long id) {
        return userActivityRepository.findById(id)
                .orElseThrow(() -> new UserActivityNotFoundException(id));
    }
}
