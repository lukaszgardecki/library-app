package com.example.libraryapp.NEWinfrastructure.persistence.jpa.bookitemrequest;

import com.example.libraryapp.NEWdomain.bookitemrequest.model.BookItemRequestStatus;
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
