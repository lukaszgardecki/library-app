package com.example.libraryapp.domain.user.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private LocalDate registrationDate;
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
