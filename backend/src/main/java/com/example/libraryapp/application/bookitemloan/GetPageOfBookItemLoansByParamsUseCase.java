package com.example.libraryapp.application.bookitemloan;

import com.example.libraryapp.application.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.domain.bookitemloan.model.BookItemLoan;
import com.example.libraryapp.domain.bookitemloan.model.BookItemLoanStatus;
import com.example.libraryapp.domain.bookitemloan.ports.BookItemLoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
class GetPageOfBookItemLoansByParamsUseCase {
    private final BookItemLoanRepository bookItemLoanRepository;
    private final BookItemRequestFacade bookItemRequestFacade;

    Page<BookItemLoan> execute(Long id, BookItemLoanStatus status, Boolean renewable, Pageable pageable) {
        Page<BookItemLoan> page = bookItemLoanRepository.findPageOfBookLoansByParams(id, status, pageable);
        Stream<BookItemLoan> stream = page.stream();

        if (Boolean.TRUE.equals(renewable)) {
            stream = stream.filter(loan -> bookItemRequestFacade.isBookItemRequested(loan.getBookItemId()));
        }
        List<BookItemLoan> filteredLoans = stream
                .filter(loan -> loan.getDueDate().isAfter(LocalDateTime.now()))
                .toList();

        int start = Math.min((int) pageable.getOffset(), filteredLoans.size());
        int end = Math.min(start + pageable.getPageSize(), filteredLoans.size());
        List<BookItemLoan> paginatedList = filteredLoans.subList(start, end);
        return new PageImpl<>(paginatedList, pageable, filteredLoans.size());
    }
}
