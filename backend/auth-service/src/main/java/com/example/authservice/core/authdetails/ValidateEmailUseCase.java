package com.example.authservice.core.authdetails;

import com.example.authservice.domain.model.authdetails.Email;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ValidateEmailUseCase {
    private final AuthDetailsService authDetailsService;

    void execute(Email username) {
        authDetailsService.validateEmail(username);
    }
}
