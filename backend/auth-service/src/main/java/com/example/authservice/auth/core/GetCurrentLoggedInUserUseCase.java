package com.example.authservice.auth.core;

import com.example.authservice.auth.domain.model.UserAuth;
import com.example.authservice.auth.domain.ports.AuthenticationManagerPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetCurrentLoggedInUserUseCase {
    private final AuthenticationService authenticationService;
    private final AuthenticationManagerPort authenticationManager;

    UserAuth execute() {
        return authenticationService.getUserAuthByEmail(authenticationManager.getCurrentUsername());
    }
}
