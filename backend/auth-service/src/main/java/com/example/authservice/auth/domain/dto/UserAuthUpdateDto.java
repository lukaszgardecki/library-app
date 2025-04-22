package com.example.authservice.auth.domain.dto;

import com.example.authservice.auth.domain.model.AccountStatus;
import com.example.authservice.auth.domain.model.Role;

public record UserAuthUpdateDto(
        String username,
        String password,
        Role role,
        AccountStatus status
) {

}
