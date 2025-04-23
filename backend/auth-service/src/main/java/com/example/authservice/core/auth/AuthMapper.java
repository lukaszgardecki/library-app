package com.example.authservice.core.auth;

import com.example.authservice.domain.dto.auth.UserAuthDto;
import com.example.authservice.domain.model.auth.UserAuth;

class AuthMapper {

    static UserAuthDto toDto(UserAuth model) {
        return new UserAuthDto(
                model.getEmail().value(),
                model.getPsswrd().value(),
                model.getRole(),
                model.getStatus(),
                model.getUserId().value()
        );
    }
}
