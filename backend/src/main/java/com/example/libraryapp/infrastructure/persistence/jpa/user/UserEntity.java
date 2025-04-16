package com.example.libraryapp.infrastructure.persistence.jpa.user;

import com.example.libraryapp.domain.user.model.AccountStatus;
import com.example.libraryapp.domain.user.model.Role;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate registrationDate;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Enumerated(EnumType.STRING)
    private Role role;
    private Integer totalBooksBorrowed;
    private Integer totalBooksRequested;
    private BigDecimal charge;
    private Long cardId;
    private Long personId;
}
