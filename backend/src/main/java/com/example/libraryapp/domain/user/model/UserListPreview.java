package com.example.libraryapp.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UserListPreview implements UserListPreviewProjection {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate registrationDate;
    private AccountStatus status;
}
