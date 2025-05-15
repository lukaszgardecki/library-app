package com.example.authservice.core.authdetails;

import com.example.authservice.domain.model.authdetails.AuthDetailsUpdate;
import com.example.authservice.domain.model.authdetails.values.Email;
import com.example.authservice.domain.model.authdetails.values.Password;
import com.example.authservice.domain.model.authdetails.values.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UpdateAuthDetailsUseCase {
    private final AuthDetailsService authService;

    void execute(UserId userId, Email email, Password password) {
        authService.updateUserCredentials(userId, email, password);
    }

    void execute(UserId userId, AuthDetailsUpdate fieldsToUpdate) {
        authService.updateAuthDetails(userId, fieldsToUpdate);
    }
}
