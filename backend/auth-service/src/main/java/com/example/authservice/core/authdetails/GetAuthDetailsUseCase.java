package com.example.authservice.core.authdetails;

import com.example.authservice.domain.model.authdetails.AuthDetails;
import com.example.authservice.domain.model.authdetails.values.Email;
import com.example.authservice.domain.model.authdetails.values.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetAuthDetailsUseCase {
    private final AuthDetailsService authDetailsService;

    AuthDetails execute(Email username) {
        return authDetailsService.getAuthDetailsByEmail(username);
    }

    AuthDetails execute(UserId username) {
        return authDetailsService.getAuthDetailsByUserId(username);
    }
}
