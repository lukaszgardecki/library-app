package com.example.authservice.core.registration;

import com.example.authservice.core.authdetails.AuthDetailsFacade;
import com.example.authservice.domain.model.authdetails.values.Email;
import com.example.authservice.domain.model.authdetails.values.Password;
import com.example.authservice.domain.model.authdetails.values.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class RegistrationService {
    private final AuthDetailsFacade authDetailsFacade;

    void validateEmail(Email email) {
        authDetailsFacade.validateEmail(email);
    }

    void save(Email email, Password password, UserId userId) {
        authDetailsFacade.createNewAuthDetails(email, password, userId);
    }
}
