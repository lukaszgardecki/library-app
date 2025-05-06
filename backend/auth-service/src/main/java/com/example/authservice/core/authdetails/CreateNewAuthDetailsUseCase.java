package com.example.authservice.core.authdetails;

import com.example.authservice.domain.model.authdetails.Email;
import com.example.authservice.domain.model.authdetails.Password;
import com.example.authservice.domain.model.authdetails.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CreateNewAuthDetailsUseCase {
    private final AuthDetailsService authDetailsService;

    void execute(Email email, Password password, UserId userId) {
        authDetailsService.save(email, password, userId);
    }
}
