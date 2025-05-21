package com.example.userservice.core.user;

import com.example.userservice.domain.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
class GetAllUsersUseCase {
    private final UserService userService;

    Page<User> execute(Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    Page<User> execute(String query, Pageable pageable) {
        return userService.getAllUsersByQuery(query, pageable);
    }
}
