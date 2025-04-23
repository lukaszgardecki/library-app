package com.example.authservice.core.auth;

import com.example.authservice.domain.model.auth.UserAuth;
import com.example.authservice.domain.ports.AuthenticationManagerPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetCurrentLoggedInUserUseCase {
    private final AuthenticationService authenticationService;
    private final AuthenticationManagerPort authenticationManager;

    UserAuth execute() {
        return authenticationService.getUserAuthByEmail(authenticationManager.getCurrentUsername());
    }
}
