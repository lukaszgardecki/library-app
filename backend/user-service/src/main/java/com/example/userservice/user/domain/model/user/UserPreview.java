package com.example.userservice.user.domain.model.user;

import com.example.userservice.user.domain.model.auth.Role;
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
