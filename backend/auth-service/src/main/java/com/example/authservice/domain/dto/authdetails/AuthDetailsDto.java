package com.example.authservice.domain.dto.authdetails;

import com.example.authservice.domain.model.authdetails.values.AccountStatus;
import com.example.authservice.domain.model.authdetails.values.Role;

public record AuthDetailsDto(
        Long id,
        String username,
        Role role,
        AccountStatus status,
        Long userId
) {

}
