package com.example.authservice.core.auth;

import com.example.authservice.domain.model.auth.Email;
import com.example.authservice.domain.model.auth.Password;
import com.example.authservice.domain.model.auth.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class SaveUserCredentialsUseCase {
    private final AuthenticationService credentialsService;

    void execute(Email username, Password password, UserId userId) {
        credentialsService.validateEmail(username);
        credentialsService.save(username, password, userId);
    }
}
