package com.example.userservice.domain.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDto extends UserUpdate {
    private String password;
}
