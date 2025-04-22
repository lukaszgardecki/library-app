package com.example.userservice.user.infrastructure.persistence.jpa;

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
    private Integer totalBooksBorrowed;
    private Integer totalBooksRequested;
    private BigDecimal charge;
    private Long cardId;
    private Long personId;
}
