package com.example.libraryapp.core.user;

import com.example.libraryapp.domain.user.model.UserDetails;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetUserDetailsUseCase {
    private final UserService userService;

    UserDetails execute(UserId id) {
        return userService.getUserDetails(id);
    }
}
