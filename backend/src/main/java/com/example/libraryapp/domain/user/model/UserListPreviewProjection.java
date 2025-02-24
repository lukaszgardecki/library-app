package com.example.libraryapp.domain.user.model;

import java.time.LocalDate;

public interface UserListPreviewProjection {
    Long getId();
    String getFirstName();
    String getLastName();
    String getEmail();
    LocalDate getRegistrationDate();
    AccountStatus getStatus();
}
