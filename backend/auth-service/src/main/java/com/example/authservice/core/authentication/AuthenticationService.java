package com.example.authservice.core.authentication;

import com.example.authservice.core.authdetails.AuthDetailsFacade;
import com.example.authservice.domain.model.authdetails.AuthDetails;
import com.example.authservice.domain.model.authdetails.values.Email;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class AuthenticationService {
    private final AuthDetailsFacade authDetailsFacade;

    AuthDetails getAuthDetailsByEmail(Email username) {
        return authDetailsFacade.getAuthDetailsByEmail(username);
    }
}
