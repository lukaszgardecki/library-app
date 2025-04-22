package com.example.userservice.user.domain.dto;

import com.example.userservice.user.domain.model.auth.AccountStatus;
import com.example.userservice.user.domain.model.auth.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserDto {
    private Long id;
    private LocalDate registrationDate;
    private String password;
    private String email;
    private AccountStatus status;
    private Role role;
    private int totalBooksBorrowed;
    private int totalBooksReserved;
    private BigDecimal charge;
    private Long cardId;
    private Long personId;
}
