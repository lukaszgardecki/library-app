package com.example.authservice.infrastructure.http.authdetails;

import com.example.authservice.domain.model.authdetails.AuthDetails;
import com.example.authservice.domain.model.authdetails.AuthDetailsUpdate;
import com.example.authservice.domain.model.authdetails.values.AccountStatus;
import com.example.authservice.domain.model.authdetails.values.Email;
import com.example.authservice.domain.model.authdetails.values.Password;
import com.example.authservice.domain.model.authdetails.values.Role;
import com.example.authservice.infrastructure.http.authdetails.dto.AuthDetailsDto;
import com.example.authservice.infrastructure.http.authdetails.dto.AuthDetailsUpdateDto;

public class AuthDetailsMapper {

    public static AuthDetailsUpdate toModel(AuthDetailsUpdateDto dto) {
        return new AuthDetailsUpdate(
                new Password(dto.password()),
                new Email(dto.username()),
                AccountStatus.valueOf(dto.status()),
                Role.valueOf(dto.status())
        );
    }

    public static AuthDetailsDto toDto(AuthDetails model) {
        return new AuthDetailsDto(
                model.getId().value(),
                model.getEmail().value(),
                model.getRole().name(),
                model.getStatus().name(),
                model.getUserId().value()
        );
    }
}
