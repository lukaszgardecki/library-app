package com.example.userservice.infrastructure.http.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserPreviewDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String role;
}
