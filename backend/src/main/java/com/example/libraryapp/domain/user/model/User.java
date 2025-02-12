package com.example.libraryapp.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class User {
    private Long id;
    private LocalDateTime registrationDate;
    private String password;
    private String email;
    private AccountStatus status;
    private Role role;
    private int totalBooksBorrowed;
    private int totalBooksRequested;
    private BigDecimal charge;
    private Long cardId;
    private Long personId;
}
