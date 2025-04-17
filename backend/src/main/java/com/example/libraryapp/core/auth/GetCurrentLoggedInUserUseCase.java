package com.example.libraryapp.core.auth;

import com.example.libraryapp.core.user.UserFacade;
import com.example.libraryapp.domain.auth.ports.AuthenticationManagerPort;
import com.example.libraryapp.domain.user.dto.UserDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetCurrentLoggedInUserUseCase {
    private final UserFacade userFacade;
    private final AuthenticationManagerPort authenticationManager;

    UserDto execute() {
        return userFacade.getUserByEmail(authenticationManager.getCurrentUsername());
    }
}
