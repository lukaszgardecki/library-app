package com.example.statisticsservice.infrastructure.persistence.jpa.usermonthlyloans;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_monthly_loans")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class UserMonthlyLoansEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private int yearValue;
    private int monthValue;
    private int loansCount;
}
