package com.example.userservice.user.core;

import com.example.userservice.user.domain.model.user.UserDetails;
import com.example.userservice.user.domain.model.user.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetUserDetailsUseCase {
    private final UserService userService;

    UserDetails execute(UserId id) {
        return userService.getUserDetails(id);
    }
}
