package com.example.statisticsservice.infrastructure.persistence.jpa.topborrowers;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "borrowers")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class BorrowerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private Integer loansCount;
    private LocalDate lastLoanDate;
}
