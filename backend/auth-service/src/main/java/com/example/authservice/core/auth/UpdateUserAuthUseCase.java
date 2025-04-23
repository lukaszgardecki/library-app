package com.example.authservice.core.auth;

import com.example.authservice.domain.dto.auth.CredentialsUpdateDto;
import com.example.authservice.domain.dto.auth.UserAuthUpdateDto;
import com.example.authservice.domain.model.auth.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UpdateUserAuthUseCase {
    private final AuthenticationService authService;

    void execute(UserId userId, CredentialsUpdateDto fieldsToUpdate) {
        authService.updateUserCredentials(userId, fieldsToUpdate);
    }

    void execute(UserId userId, UserAuthUpdateDto fieldsToUpdate) {
        authService.updateUserAuth(userId, fieldsToUpdate);
    }
}
