package com.example.authservice.core.authdetails;

import com.example.authservice.domain.dto.auth.CredentialsUpdateDto;
import com.example.authservice.domain.dto.authdetails.AuthDetailsUpdateDto;
import com.example.authservice.domain.model.authdetails.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UpdateAuthDetailsUseCase {
    private final AuthDetailsService authService;

    void execute(UserId userId, CredentialsUpdateDto fieldsToUpdate) {
        authService.updateUserCredentials(userId, fieldsToUpdate);
    }

    void execute(UserId userId, AuthDetailsUpdateDto fieldsToUpdate) {
        authService.updateAuthDetails(userId, fieldsToUpdate);
    }
}
