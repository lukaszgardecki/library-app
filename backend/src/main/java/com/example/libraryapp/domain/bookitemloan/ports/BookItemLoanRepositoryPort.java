package com.example.libraryapp.domain.bookitemloan.ports;

import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.bookitemloan.model.*;
import com.example.libraryapp.domain.user.model.UserId;
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

    Page<BookItemLoanListPreviewProjection> findPageOfBookLoanListPreviews(UserId userId, String query, LoanStatus status, Pageable pageable);

    List<Object[]> findTopSubjectsWithLoansCount(int limit);

    BookItemLoan save(BookItemLoan bookItemLoan);

    long countByCreationDate(LocalDate date);

    long countUniqueBorrowersInCurrentMonth();

    List<Object[]> countBookItemLoansMonthly(LocalDate startDate, LocalDate endDate);

    List<Object[]> countBookItemLoansDaily(LocalDate startDate, LocalDate endDate, LoanStatus status);
}
