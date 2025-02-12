package com.example.libraryapp.infrastructure.persistence.jpa.bookitemloan;

import com.example.libraryapp.domain.bookitemloan.model.BookItemLoanStatus;
import jakarta.persistence.*;
import lombok.*;

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
    private LocalDateTime dueDate;
    private LocalDateTime returnDate;

    @Enumerated(EnumType.STRING)
    private BookItemLoanStatus status;
    private Long userId;
    private Long bookItemId;
}
