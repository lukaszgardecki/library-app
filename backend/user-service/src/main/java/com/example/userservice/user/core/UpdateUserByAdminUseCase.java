package com.example.userservice.user.core;

import com.example.userservice.user.domain.dto.UserUpdateAdminDto;
import com.example.userservice.user.domain.model.user.User;
import com.example.userservice.user.domain.model.user.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UpdateUserByAdminUseCase {
    private final UserService userService;


    User execute(UserId id, UserUpdateAdminDto userData) {
        return userService.updateUserByAdmin(id, userData);
    }
}
