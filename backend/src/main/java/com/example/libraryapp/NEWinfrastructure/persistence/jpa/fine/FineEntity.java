package com.example.libraryapp.NEWinfrastructure.persistence.jpa.fine;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "fine")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class FineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long loanId;
    private BigDecimal amount;
    private Boolean paid;
}
