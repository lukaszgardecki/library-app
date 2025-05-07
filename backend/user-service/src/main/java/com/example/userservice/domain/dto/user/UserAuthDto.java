package com.example.userservice.domain.dto.user;

import com.example.userservice.domain.integration.auth.AccountStatus;
import com.example.userservice.domain.integration.auth.Role;

public record UserAuthDto(
        String username,
        String password,
        Role role,
        AccountStatus status,
        Long userId
) {

}
