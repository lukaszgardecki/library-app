package com.example.userservice.user.domain.dto;

import com.example.userservice.user.domain.model.auth.AccountStatus;
import com.example.userservice.user.domain.model.auth.Role;

public record UserAuthDto(
        String username,
        String password,
        Role role,
        AccountStatus status,
        Long userId
) {

}
