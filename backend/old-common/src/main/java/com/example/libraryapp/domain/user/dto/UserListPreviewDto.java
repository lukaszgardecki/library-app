package com.example.libraryapp.domain.user.dto;

import com.example.userservice.common.user.model.AccountStatus;
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
