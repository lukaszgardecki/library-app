package com.example.authservice.domain.dto.authdetails;

import com.example.authservice.domain.model.authdetails.AccountStatus;
import com.example.authservice.domain.model.authdetails.Role;

public record AuthDetailsUpdateDto(
        String username,
        String password,
        Role role,
        AccountStatus status
) {

}
