package com.example.userservice.core.user;

import com.example.userservice.domain.model.user.UserDetailsAdmin;
import com.example.userservice.domain.model.user.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetUserDetailsAdminUseCase {
    private final UserService userService;

    UserDetailsAdmin execute(UserId id) {
        return userService.getAdminUserDetailsById(id);
    }
}
