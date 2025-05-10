package com.example.statisticsservice.core;

import com.example.statisticsservice.domain.integration.LoanCreationDate;
import com.example.statisticsservice.domain.integration.LoanReturnDate;
import com.example.statisticsservice.domain.integration.Subject;
import com.example.statisticsservice.domain.model.borrower.Borrower;
import com.example.statisticsservice.domain.model.borrower.values.LoansCount;
import com.example.statisticsservice.domain.model.borrower.values.PersonFirstName;
import com.example.statisticsservice.domain.model.borrower.values.PersonLastName;
import com.example.statisticsservice.domain.model.borrower.values.UserId;
import com.example.statisticsservice.domain.ports.in.EventListenerPort;
import com.example.statisticsservice.domain.ports.out.*;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
class EventListenerService implements EventListenerPort {
    private final BorrowerRepositoryPort borrowerRepository;
    private final CityRepositoryPort cityRepository;
    private final DailyStatsRepositoryPort dailyStatsRepository;
    private final GenreRepositoryPort genreRepositoryPort;
    private final UserMonthlyLoansRepositoryPort userMonthlyLoansRepository;

    @Override
    public void handleUserCreated(
            UserId userId, PersonFirstName firstName, PersonLastName lastName, LocalDate birthday, String cityName
    ) {
        Borrower newBorrower = createNewBorrower(userId, firstName, lastName, birthday);
        borrowerRepository.saveNewBorrower(newBorrower);
        cityRepository.incrementUsersCount(cityName);
        dailyStatsRepository.incrementNewUsersCount(LocalDate.now());
    }

    @Override
    public void handleLoanCreated(UserId userId, LoanCreationDate loanCreationDate, Subject subject) {
        userMonthlyLoansRepository.incrementUserLoansCount(userId, loanCreationDate);
        borrowerRepository.incrementLoansCount(userId);
        borrowerRepository.changeLastLoanDate(userId, loanCreationDate);
        dailyStatsRepository.incrementNewLoansCount(loanCreationDate);
        genreRepositoryPort.incrementLoansCount(subject);
    }

    @Override
    public void handleBookItemReturned(LoanReturnDate returnDate) {
        dailyStatsRepository.incrementReturnedLoansCount(returnDate);
    }

    private Borrower createNewBorrower(UserId userId, PersonFirstName firstName, PersonLastName lastName, LocalDate birthday) {
        return Borrower.builder()
                .userId(userId)
                .firstName(firstName)
                .lastName(lastName)
                .birthday(birthday)
                .loans(new LoansCount(0))
                .build();
    }
}
