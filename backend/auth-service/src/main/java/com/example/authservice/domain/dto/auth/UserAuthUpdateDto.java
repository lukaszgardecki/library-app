package com.example.authservice.domain.dto.auth;

import com.example.authservice.domain.model.auth.AccountStatus;
import com.example.authservice.domain.model.auth.Role;

public record UserAuthUpdateDto(
        String username,
        String password,
        Role role,
        AccountStatus status
) {

}
