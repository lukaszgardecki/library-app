package com.example.authservice.domain.model.authdetails;

import com.example.authservice.domain.model.authdetails.values.AccountStatus;
import com.example.authservice.domain.model.authdetails.values.Email;
import com.example.authservice.domain.model.authdetails.values.Password;
import com.example.authservice.domain.model.authdetails.values.Role;

public record AuthDetailsUpdate(
        Password psswrd,
        Email email,
        AccountStatus status,
        Role role
) { }
