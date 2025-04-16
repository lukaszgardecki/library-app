package com.example.libraryapp.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserPreview {
    private Long id;
    private String firstName;
    private String lastName;
    private Role role;
}
