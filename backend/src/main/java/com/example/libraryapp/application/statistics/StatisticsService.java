package com.example.libraryapp.application.statistics;

import com.example.libraryapp.application.bookitemloan.BookItemLoanFacade;
import com.example.libraryapp.application.person.PersonFacade;
import com.example.libraryapp.application.user.UserFacade;
import com.example.libraryapp.domain.bookitemloan.dto.BookItemLoanDto;
import com.example.libraryapp.domain.bookitemloan.model.LoanCreationDate;
import com.example.libraryapp.domain.bookitemloan.model.LoanStatus;
import com.example.libraryapp.domain.person.dto.PersonDto;
import com.example.libraryapp.domain.person.model.PersonId;
import com.example.libraryapp.domain.statistics.dto.UserTopBorrowersDto;
import com.example.libraryapp.domain.user.dto.UserDto;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class StatisticsService {
    private final PersonFacade personFacade;
    private final UserFacade userFacade;
    private final BookItemLoanFacade bookItemLoanFacade;

    long countBookItemLoansByDay(LoanCreationDate day) {
        return bookItemLoanFacade.countByCreationDate(day);
    }

    List<Object[]> countBookItemLoansByDay(LocalDate startDate, LocalDate endDate, LoanStatus status) {
        return bookItemLoanFacade.countBookItemLoansDaily(startDate, endDate, status);
    }

    List<Object[]> countBookItemLoansByMonth(LocalDate startDate, LocalDate endDate) {
        return bookItemLoanFacade.countBookItemLoansMonthly(startDate, endDate);
    }

    long countUniqueBorrowersInCurrentMonth() {
        return bookItemLoanFacade.countUniqueBorrowersInCurrentMonth();
    }

    long countNewRegisteredUsersInCurrentMonth() {
        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();
        return userFacade.countNewRegisteredUsersByMonth(currentMonth, currentYear);
    }

    long countUsersByAgeBetween(int min, int max) {
        return personFacade.countByAgeBetween(min, max);
    }

    long countAllUsers() {
        return userFacade.countAllUsers();
    }

    Map<String, Long> getTopSubjectsWithLoansCount(int limit) {
        return bookItemLoanFacade.getTopSubjectsWithLoansCountUseCase(limit);
    }

    List<UserTopBorrowersDto> getTopBorrowers(int limit) {
        List<UserDto> topBorrowers = userFacade.getAllByLoanCountDesc(limit);
        return topBorrowers.stream()
                .map(user -> {
                    PersonDto person = personFacade.getPersonById(new PersonId(user.getPersonId()));
                    return new UserTopBorrowersDto(
                            user.getId(),
                            topBorrowers.indexOf(user) + 1,
                            String.format("%s %s", person.getFirstName(), person.getLastName()),
                            user.getTotalBooksBorrowed()
                    );
                })
                .collect(Collectors.toList());
    }

    Map<String, Long> getTopCitiesByUserCount(int limit) {
        return personFacade.getCitiesByUserCountDesc(limit);
    }

    Map<YearMonth, Long> getUserLoansPerMonth(UserId userId) {
        List<BookItemLoanDto> loans = bookItemLoanFacade.getAllLoansByUserId(userId);
        Map<YearMonth, Long> loansCountMap = loans.stream()
                .collect(Collectors.groupingBy(
                        loan -> YearMonth.from(loan.creationDate()),
                        Collectors.counting()
                ));
        return loansCountMap;
    }
}
