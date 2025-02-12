package com.example.libraryapp.application.bookitemloan;

import com.example.libraryapp.application.constants.Constants;
import com.example.libraryapp.domain.bookitemloan.exceptions.BookItemLoanException;
import com.example.libraryapp.domain.bookitemloan.exceptions.BookItemLoanNotFoundException;
import com.example.libraryapp.domain.bookitemloan.model.BookItemLoan;
import com.example.libraryapp.domain.bookitemloan.model.BookItemLoanStatus;
import com.example.libraryapp.domain.bookitemloan.ports.BookItemLoanRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
class BookItemLoanService {
    private final BookItemLoanRepository bookItemLoanRepository;

    BookItemLoan getBookItemLoanById(Long id) {
        return bookItemLoanRepository.findById(id)
                .orElseThrow(() -> new BookItemLoanNotFoundException(id));
    }

    BookItemLoan getBookItemLoan(Long bookItemId, Long userId, BookItemLoanStatus status) {
        return bookItemLoanRepository.findByParams(bookItemId, userId, status)
                .orElseThrow(BookItemLoanNotFoundException::new);
    }

    List<BookItemLoan> getAllBookItemLoans(Long userId) {
        return bookItemLoanRepository.findAllByUserId(userId);
    }

    List<Object[]> getTopSubjectsWithLoansCount(int limit) {
        return bookItemLoanRepository.findTopSubjectsWithLoansCount(limit);
    }

    BookItemLoan save(BookItemLoan bookItemLoan) {
        return bookItemLoanRepository.save(bookItemLoan);
    }

    BookItemLoan saveLoan(Long bookItemId, Long userId, Long bookId) {
        BookItemLoan bookItemLoan = createBookItemLoanToSave(bookItemId, userId, bookId);
        return bookItemLoanRepository.save(bookItemLoan);
    }

    void validateBookItemLoanForRenewal(BookItemLoan loanToUpdate) {
        if (loanToUpdate.getDueDate().isBefore(LocalDateTime.now())) {
            throw new BookItemLoanException("Message.RESERVATION_ALREADY_CREATED.getMessage()");
        }
    }

    long countByCreationDate(LocalDateTime date) {
        return bookItemLoanRepository.countByCreationDate(date.toLocalDate());
    }

    long countUniqueBorrowersInCurrentMonth() {
        return bookItemLoanRepository.countUniqueBorrowersInCurrentMonth();
    }

    List<Object[]> countBookItemLoansMonthly(LocalDate startDate, LocalDate endDate) {
        return bookItemLoanRepository.countBookItemLoansMonthly(startDate, endDate);
    }

    List<Object[]> countBookItemLoansDaily(LocalDate startDate, LocalDate endDate, BookItemLoanStatus status) {
        return bookItemLoanRepository.countBookItemLoansDaily(startDate, endDate, status);
    }

    private BookItemLoan createBookItemLoanToSave(Long bookItemId, Long userId, Long bookId) {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusDays(Constants.MAX_LENDING_DAYS);
        return BookItemLoan.builder()
                .bookItemId(bookItemId)
                .userId(userId)
                .bookId(bookId)
                .status(BookItemLoanStatus.CURRENT)
                .creationDate(startTime)
                .dueDate(endTime)
                .build();
    }
}
