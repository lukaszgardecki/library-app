package com.example.apigateway.domain.dto;

import com.example.apigateway.domain.model.AccountStatus;
import com.example.apigateway.domain.model.Role;

public record UserAuthDto(
        String username,
        Role role,
        AccountStatus status,
        Long userId
) {

}
