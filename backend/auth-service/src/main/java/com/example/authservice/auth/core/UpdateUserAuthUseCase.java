package com.example.authservice.auth.core;

import com.example.authservice.auth.domain.dto.CredentialsUpdateDto;
import com.example.authservice.auth.domain.dto.UserAuthUpdateDto;
import com.example.authservice.auth.domain.model.UserId;
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
