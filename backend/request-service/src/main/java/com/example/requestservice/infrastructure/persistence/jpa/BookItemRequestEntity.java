package com.example.requestservice.infrastructure.persistence.jpa;

import com.example.requestservice.domain.model.values.BookItemRequestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "book_request")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class BookItemRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime creationDate;

    @Enumerated(EnumType.STRING)
    private BookItemRequestStatus status;

    private Long userId;
    private Long bookItemId;
}
