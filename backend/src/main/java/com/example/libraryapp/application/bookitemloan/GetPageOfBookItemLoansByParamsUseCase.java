package com.example.libraryapp.application.bookitemloan;

import com.example.libraryapp.application.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.domain.bookitemloan.model.BookItemLoan;
import com.example.libraryapp.domain.bookitemloan.model.BookItemLoanStatus;
import com.example.libraryapp.domain.bookitemloan.ports.BookItemLoanRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@RequiredArgsConstructor
class GetPageOfBookItemLoansByParamsUseCase {
    private final BookItemLoanRepositoryPort bookItemLoanRepository;
    private final BookItemRequestFacade bookItemRequestFacade;

    Page<BookItemLoan> execute(Long userId, BookItemLoanStatus status, Boolean renewable, Pageable pageable) {
        Predicate<BookItemLoan> byRenewable = (loan) ->
                Objects.equals(renewable, Boolean.TRUE)
                && !loan.getDueDate().isBefore(LocalDate.now().atStartOfDay())
                && !bookItemRequestFacade.isBookItemRequested(loan.getBookItemId());
        List<BookItemLoan> filteredLoans = bookItemLoanRepository.findPageOfBookLoansByParams(userId, status, pageable)
                .stream()
                .filter(byRenewable)
                .toList();

        int start = Math.min((int) pageable.getOffset(), filteredLoans.size());
        int end = Math.min(start + pageable.getPageSize(), filteredLoans.size());
        List<BookItemLoan> paginatedList = filteredLoans.subList(start, end);
        return new PageImpl<>(paginatedList, pageable, filteredLoans.size());
    }
}
