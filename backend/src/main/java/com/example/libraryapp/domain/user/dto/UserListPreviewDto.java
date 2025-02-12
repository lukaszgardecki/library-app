package com.example.libraryapp.domain.user.dto;

import com.example.libraryapp.domain.user.model.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UserListPreviewDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime registrationDate;
    private AccountStatus status;
}
