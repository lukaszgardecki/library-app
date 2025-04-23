package com.example.authservice.core.auth;

import com.example.authservice.domain.model.auth.Email;
import com.example.authservice.domain.model.auth.UserAuth;
import com.example.authservice.domain.model.auth.UserId;
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
