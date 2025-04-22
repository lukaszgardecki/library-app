package com.example.userservice.user.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDto extends UserUpdate {
    private String password;
}
