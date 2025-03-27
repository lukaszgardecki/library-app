package com.example.libraryapp.application.bookitemloan;

import com.example.libraryapp.domain.Constants;
import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.book.model.BookId;
import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.bookitemloan.exceptions.BookItemLoanException;
import com.example.libraryapp.domain.bookitemloan.exceptions.BookItemLoanNotFoundException;
import com.example.libraryapp.domain.bookitemloan.model.*;
import com.example.libraryapp.domain.bookitemloan.ports.BookItemLoanRepositoryPort;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
class BookItemLoanService {
    private final BookItemLoanRepositoryPort bookItemLoanRepository;

    BookItemLoan getBookItemLoanById(LoanId id) {
        return bookItemLoanRepository.findById(id)
                .orElseThrow(() -> new BookItemLoanNotFoundException(id));
    }

    BookItemLoan getBookItemLoan(BookItemId bookItemId, UserId userId, LoanStatus status) {
        return bookItemLoanRepository.findByParams(bookItemId, userId, status)
                .orElseThrow(BookItemLoanNotFoundException::new);
    }

    BookItemLoan getBookItemLoan(BookItemId bookItemId, LoanStatus status) {
        return bookItemLoanRepository.findByParams(bookItemId, status)
                .orElseThrow(BookItemLoanNotFoundException::new);
    }

    List<BookItemLoan> getAllBookItemLoans(UserId userId) {
        return bookItemLoanRepository.findAllByUserId(userId);
    }

    List<Object[]> getTopSubjectsWithLoansCount(int limit) {
        return bookItemLoanRepository.findTopSubjectsWithLoansCount(limit);
    }

    BookItemLoan save(BookItemLoan bookItemLoan) {
        return bookItemLoanRepository.save(bookItemLoan);
    }

    BookItemLoan saveLoan(BookItemId bookItemId, UserId userId, BookId bookId) {
        BookItemLoan bookItemLoan = createBookItemLoanToSave(bookItemId, userId, bookId);
        return bookItemLoanRepository.save(bookItemLoan);
    }

    void validateBookItemLoanForRenewal(BookItemLoan loanToUpdate) {
        if (loanToUpdate.getDueDate().value().isBefore(LocalDateTime.now())) {
            throw new BookItemLoanException(MessageKey.LOAN_RENEWAL_FAILED_RETURN_DATE);
        }
    }

    long countByCreationDate(LoanCreationDate date) {
        return bookItemLoanRepository.countByCreationDate(date.value().toLocalDate());
    }

    long countUniqueBorrowersInCurrentMonth() {
        return bookItemLoanRepository.countUniqueBorrowersInCurrentMonth();
    }

    List<Object[]> countBookItemLoansMonthly(LocalDate startDate, LocalDate endDate) {
        return bookItemLoanRepository.countBookItemLoansMonthly(startDate, endDate);
    }

    List<Object[]> countBookItemLoansDaily(LocalDate startDate, LocalDate endDate, LoanStatus status) {
        return bookItemLoanRepository.countBookItemLoansDaily(startDate, endDate, status);
    }

    private BookItemLoan createBookItemLoanToSave(BookItemId bookItemId, UserId userId, BookId bookId) {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusDays(Constants.MAX_LENDING_DAYS);
        return BookItemLoan.builder()
                .bookItemId(bookItemId)
                .userId(userId)
                .status(LoanStatus.CURRENT)
                .creationDate(new LoanCreationDate(startTime))
                .dueDate(new LoanDueDate(endTime))
                .build();
    }
}
