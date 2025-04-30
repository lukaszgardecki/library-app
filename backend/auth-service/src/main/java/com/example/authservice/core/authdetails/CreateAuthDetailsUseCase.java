package com.example.authservice.core.authdetails;

import com.example.authservice.domain.model.authdetails.Email;
import com.example.authservice.domain.model.authdetails.Password;
import com.example.authservice.domain.model.authdetails.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CreateAuthDetailsUseCase {
    private final AuthDetailsService credentialsService;

    void execute(Email username, Password password, UserId userId) {
        credentialsService.validateEmail(username);
        credentialsService.save(username, password, userId);
    }
}
