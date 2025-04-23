package com.example.userservice.core.user;

import com.example.userservice.domain.dto.user.UserUpdateAdminDto;
import com.example.userservice.domain.model.user.User;
import com.example.userservice.domain.model.user.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UpdateUserByAdminUseCase {
    private final UserService userService;


    User execute(UserId id, UserUpdateAdminDto userData) {
        return userService.updateUserByAdmin(id, userData);
    }
}
