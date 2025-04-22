package com.example.userservice.user.domain.dto;

import com.example.userservice.user.domain.model.auth.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UserListPreviewDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate registrationDate;
    private AccountStatus status;
}
