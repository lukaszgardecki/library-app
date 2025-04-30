package com.example.authservice.core.authdetails;

import com.example.authservice.domain.dto.authdetails.AuthDetailsDto;
import com.example.authservice.domain.model.authdetails.AuthDetails;

class AuthDetailsMapper {

    static AuthDetailsDto toDto(AuthDetails model) {
        return new AuthDetailsDto(
                model.getId().value(),
                model.getEmail().value(),
                model.getRole(),
                model.getStatus(),
                model.getUserId().value()
        );
    }
}
