package com.example.libraryapp.application.user;

import com.example.libraryapp.domain.user.dto.UserUpdateAdminDto;
import com.example.libraryapp.domain.user.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UpdateUserByAdminUseCase {
    private final UserService userService;


    User execute(Long id, UserUpdateAdminDto userData) {
        return userService.updateUserByAdmin(id, userData);
    }
}
