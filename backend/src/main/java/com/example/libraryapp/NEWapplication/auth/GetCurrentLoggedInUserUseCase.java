package com.example.libraryapp.NEWapplication.auth;

import com.example.libraryapp.NEWapplication.user.UserFacade;
import com.example.libraryapp.NEWdomain.auth.ports.AuthenticationManagerPort;
import com.example.libraryapp.NEWdomain.user.dto.UserDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetCurrentLoggedInUserUseCase {
    private final UserFacade userFacade;
    private final AuthenticationManagerPort authenticationManager;

    UserDto execute() {
        return userFacade.getUserByEmail(authenticationManager.getCurrentUsername());
    }
}
