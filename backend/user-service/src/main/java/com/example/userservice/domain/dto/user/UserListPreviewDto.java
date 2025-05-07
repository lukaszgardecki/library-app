package com.example.userservice.domain.dto.user;

import com.example.userservice.domain.integration.auth.AccountStatus;
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
