package com.example.loanservice.domain.ports.out;

import com.example.loanservice.domain.model.BookItemLoan;
import com.example.loanservice.domain.model.values.BookItemId;
import com.example.loanservice.domain.model.values.LoanId;
import com.example.loanservice.domain.model.values.LoanStatus;
import com.example.loanservice.domain.model.values.UserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookItemLoanRepositoryPort {

    Optional<BookItemLoan> findById(LoanId id);

    Optional<BookItemLoan> findByParams(BookItemId bookItemId, UserId userId, LoanStatus status);

    Optional<BookItemLoan> findByParams(BookItemId bookItemId, LoanStatus status);

    List<BookItemLoan> findAllByUserId(UserId userId);

    List<BookItemLoan> findAllCurrentLoansByUserId(UserId userId);

    Page<BookItemLoan> findPageOfBookLoansByParams(UserId userId, LoanStatus status, Pageable pageable);

    List<Object[]> findTopSubjectsWithLoansCount(int limit);

    BookItemLoan save(BookItemLoan bookItemLoan);

    long countByCreationDate(LocalDate date);

    long countUniqueBorrowersInCurrentMonth();

    List<Object[]> countBookItemLoansMonthly(LocalDate startDate, LocalDate endDate);

    List<Object[]> countBookItemLoansDaily(LocalDate startDate, LocalDate endDate, LoanStatus status);
}
