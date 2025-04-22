package com.example.authservice.auth.core;

import com.example.authservice.auth.domain.dto.UserAuthDto;
import com.example.authservice.auth.domain.model.UserAuth;

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
