package com.example.libraryapp.domain.user.dto;

import com.example.userservice.common.user.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserPreviewDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Role role;
}
