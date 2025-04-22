package com.example.authservice.auth.core;

import com.example.authservice.auth.domain.model.Email;
import com.example.authservice.auth.domain.model.UserAuth;
import com.example.authservice.auth.domain.model.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetUserAuthUseCase {
    private final AuthenticationService authenticationService;

    UserAuth execute(Email username) {
        return authenticationService.getUserAuthByEmail(username);
    }

    UserAuth execute(UserId username) {
        return authenticationService.getUserAuthByUserId(username);
    }
}
