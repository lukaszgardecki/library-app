package com.example.libraryapp.NEWapplication.useractivity;

import com.example.libraryapp.NEWdomain.useractivity.dto.UserActivityDto;
import com.example.libraryapp.NEWdomain.useractivity.model.UserActivity;
import com.example.libraryapp.NEWdomain.useractivity.ports.UserActivityRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class SaveActivityUseCase {
    private final UserActivityRepository userActivityRepository;

    UserActivity execute(UserActivityDto activity) {
        return userActivityRepository.save(UserActivityMapper.toModel(activity));
    }
}
