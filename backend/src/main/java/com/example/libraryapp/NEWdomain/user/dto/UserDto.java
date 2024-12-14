package com.example.libraryapp.NEWdomain.user.dto;

import com.example.libraryapp.NEWdomain.user.model.AccountStatus;
import com.example.libraryapp.NEWdomain.user.model.Role;
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
public class UserDto {
    private Long id;
    private LocalDateTime registrationDate;
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
