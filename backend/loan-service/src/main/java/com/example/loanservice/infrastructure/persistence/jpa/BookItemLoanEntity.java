package com.example.loanservice.infrastructure.persistence.jpa;

import com.example.loanservice.domain.model.values.LoanStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_loan")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class BookItemLoanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime creationDate;
    private LocalDate dueDate;
    private LocalDateTime returnDate;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;
    private Long userId;
    private Long bookItemId;
}
