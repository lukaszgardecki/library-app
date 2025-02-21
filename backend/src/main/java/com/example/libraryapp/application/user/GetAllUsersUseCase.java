package com.example.libraryapp.application.user;

import com.example.libraryapp.domain.user.model.User;
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
