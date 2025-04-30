package com.example.authservice.core.authentication;

import com.example.authservice.domain.dto.token.AuthDto;
import com.example.authservice.domain.model.token.Auth;

class AuthMapper {

    static AuthDto toDto(Auth auth) {
        return new AuthDto(
                auth.accessToken().getToken(),
                auth.refreshToken().getToken(),
                auth.cookieValues().cookieName(),
                auth.cookieValues().text()
        );
    }
}
