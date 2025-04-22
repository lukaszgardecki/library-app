package com.example.userservice.user.domain.model.user;

import com.example.userservice.user.domain.model.auth.AccountStatus;

import java.time.LocalDate;

public interface UserListPreviewProjection {
    Long getId();
    String getFirstName();
    String getLastName();
    String getEmail();
    LocalDate getRegistrationDate();
    AccountStatus getStatus();
}
