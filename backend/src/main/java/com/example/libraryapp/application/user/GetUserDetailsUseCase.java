package com.example.libraryapp.application.user;

import com.example.libraryapp.domain.user.model.UserDetails;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetUserDetailsUseCase {
    private final UserService userService;

    UserDetails execute(Long id) {
        return userService.getUserDetails(id);
    }
}
