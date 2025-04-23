package com.example.userservice.infrastructure.persistence.jpa.librarycard;

import com.example.userservice.domain.model.librarycard.LibraryCardStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "library_card")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class LibraryCardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String barcode;
    private LocalDateTime issuedAt;

    @Enumerated(EnumType.STRING)
    private LibraryCardStatus status;
    private Long userId;
}
