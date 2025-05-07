package com.example.authservice.domain.dto.authdetails;

import com.example.authservice.domain.model.authdetails.values.AccountStatus;
import com.example.authservice.domain.model.authdetails.values.Role;

public record AuthDetailsUpdateDto(
        String username,
        String password,
        Role role,
        AccountStatus status
) {

}
