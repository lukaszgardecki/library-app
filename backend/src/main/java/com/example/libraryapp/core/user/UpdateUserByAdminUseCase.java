package com.example.libraryapp.core.user;

import com.example.libraryapp.domain.user.dto.UserUpdateAdminDto;
import com.example.libraryapp.domain.user.model.User;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UpdateUserByAdminUseCase {
    private final UserService userService;


    User execute(UserId id, UserUpdateAdminDto userData) {
        return userService.updateUserByAdmin(id, userData);
    }
}
