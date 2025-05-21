package com.example.statisticsservice.domain.model.borrower;

import com.example.statisticsservice.domain.integration.BirthDate;
import com.example.statisticsservice.domain.model.borrower.values.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Borrower {
    private BorrowerId id;
    private UserId userId;
    private PersonFirstName firstName;
    private PersonLastName lastName;
    private BirthDate birthday;
    private LoansCount loans;
    private LocalDate lastLoanDate;
}
