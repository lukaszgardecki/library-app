package com.example.libraryapp.domain.bookitemloan.ports;

import com.example.libraryapp.domain.bookitemloan.model.BookItemLoan;
import com.example.libraryapp.domain.bookitemloan.model.BookItemLoanStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookItemLoanRepository {

    Optional<BookItemLoan> findById(Long id);

    Optional<BookItemLoan> findByParams(Long bookItemId, Long userId, BookItemLoanStatus status);

    Optional<BookItemLoan> findByParams(Long bookItemId, BookItemLoanStatus status);

    List<BookItemLoan> findAllByUserId(Long userId);

    List<BookItemLoan> findAllCurrentLoansByUserId(Long userId);

    Page<BookItemLoan> findPageOfBookLoansByParams(Long id, BookItemLoanStatus status, Pageable pageable);

    List<Object[]> findTopSubjectsWithLoansCount(int limit);

    BookItemLoan save(BookItemLoan bookItemLoan);

    long countByCreationDate(LocalDate date);

    long countUniqueBorrowersInCurrentMonth();

    List<Object[]> countBookItemLoansMonthly(LocalDate startDate, LocalDate endDate);

    List<Object[]> countBookItemLoansDaily(LocalDate startDate, LocalDate endDate, BookItemLoanStatus status);
}
