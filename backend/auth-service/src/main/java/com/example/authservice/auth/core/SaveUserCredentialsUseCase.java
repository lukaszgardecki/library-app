package com.example.authservice.auth.core;

import com.example.authservice.auth.domain.model.Email;
import com.example.authservice.auth.domain.model.Password;
import com.example.authservice.auth.domain.model.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class SaveUserCredentialsUseCase {
    private final AuthenticationService credentialsService;

    void execute(Email username, Password password, UserId userId) {
        credentialsService.validateEmail(username);
        credentialsService.save(username, password, userId);
    }
}
