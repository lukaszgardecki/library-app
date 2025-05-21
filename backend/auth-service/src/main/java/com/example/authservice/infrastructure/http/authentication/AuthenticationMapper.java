package com.example.authservice.infrastructure.http.authentication;

import com.example.authservice.domain.model.token.Auth;
import com.example.authservice.infrastructure.http.authentication.dto.AuthDto;

class AuthenticationMapper {

    static AuthDto toDto(Auth auth) {
        return new AuthDto(
                auth.accessToken().getToken(),
                auth.refreshToken().getToken(),
                auth.cookieValues().cookieName(),
                auth.cookieValues().text()
        );
    }
}
