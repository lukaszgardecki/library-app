package com.example.userservice.user.core;

import com.example.userservice.user.domain.model.user.UserDetailsAdmin;
import com.example.userservice.user.domain.model.user.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetUserDetailsAdminUseCase {
    private final UserService userService;

    UserDetailsAdmin execute(UserId id) {
        return userService.getAdminUserDetailsById(id);
    }
}
