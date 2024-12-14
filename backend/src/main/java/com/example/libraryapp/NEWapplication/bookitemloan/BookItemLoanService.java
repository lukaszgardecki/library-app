package com.example.libraryapp.NEWapplication.bookitemloan;

import com.example.libraryapp.NEWapplication.constants.Constants;
import com.example.libraryapp.NEWdomain.bookitemloan.exceptions.BookItemLoanException;
import com.example.libraryapp.NEWdomain.bookitemloan.exceptions.BookItemLoanNotFoundException;
import com.example.libraryapp.NEWdomain.bookitemloan.model.BookItemLoan;
import com.example.libraryapp.NEWdomain.bookitemloan.model.BookItemLoanStatus;
import com.example.libraryapp.NEWdomain.bookitemloan.ports.BookItemLoanRepository;
import com.example.libraryapp.NEWdomain.bookitemrequest.exceptions.BookItemRequestException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class BookItemLoanService {
    private final BookItemLoanRepository bookItemLoanRepository;

    BookItemLoan getBookItemLoan(Long id) {
        return bookItemLoanRepository.findById(id)
                .orElseThrow(() -> new BookItemLoanNotFoundException(id));
    }

    BookItemLoan getBookItemLoan(Long bookItemId, Long userId, BookItemLoanStatus status) {
        return bookItemLoanRepository.findByParams(bookItemId, userId, status)
                .orElseThrow(BookItemLoanNotFoundException::new);
    }

    BookItemLoan save(BookItemLoan bookItemLoan) {
        return bookItemLoanRepository.save(bookItemLoan);
    }

    BookItemLoan saveLoan(Long bookItemId, Long userId, Long bookId) {
        BookItemLoan bookItemLoan = createBookItemLoanToSave(bookItemId, userId, bookId);
        return bookItemLoanRepository.save(bookItemLoan);
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

    void validateBookItemLoanForRenewal(BookItemLoan loanToUpdate) {
        if (loanToUpdate.getDueDate().isBefore(LocalDateTime.now())) {
            throw new BookItemLoanException("Message.RESERVATION_ALREADY_CREATED.getMessage()");
        }
    }
}
