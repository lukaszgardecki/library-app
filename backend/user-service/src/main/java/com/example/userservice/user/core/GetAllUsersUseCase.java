package com.example.userservice.user.core;

import com.example.userservice.user.domain.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
class GetAllUsersUseCase {
    private final UserService userService;

    Page<User> execute(Pageable pageable) {
        return userService.getAllUsers(pageable);
    }
}
