package com.example.userservice.core.user;

import com.example.userservice.domain.model.user.UserDetails;
import com.example.userservice.domain.model.user.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetUserDetailsUseCase {
    private final UserService userService;

    UserDetails execute(UserId id) {
        return userService.getUserDetails(id);
    }
}
