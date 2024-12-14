package com.example.libraryapp.NEWdomain.bookitemloan.ports;

import com.example.libraryapp.NEWdomain.bookitemloan.model.BookItemLoan;
import com.example.libraryapp.NEWdomain.bookitemloan.model.BookItemLoanStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookItemLoanRepository {

    Optional<BookItemLoan> findById(Long id);

    Optional<BookItemLoan> findByParams(Long bookItemId, Long userId, BookItemLoanStatus status);

    Page<BookItemLoan> findPageOfBookLoansByParams(Long id, BookItemLoanStatus status, Pageable pageable);

    BookItemLoan save(BookItemLoan bookItemLoan);
}
