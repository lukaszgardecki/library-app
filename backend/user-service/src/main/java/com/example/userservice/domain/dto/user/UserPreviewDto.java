package com.example.userservice.domain.dto.user;

import com.example.userservice.domain.model.auth.Role;
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
