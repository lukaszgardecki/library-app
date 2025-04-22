package com.example.libraryapp.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDto extends UserUpdate {
    private String password;
}
