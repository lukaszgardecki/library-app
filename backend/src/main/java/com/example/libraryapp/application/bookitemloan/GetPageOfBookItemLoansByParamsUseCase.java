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
import java.util.function.Predicate;

@RequiredArgsConstructor
class GetPageOfBookItemLoansByParamsUseCase {
    private final BookItemLoanRepositoryPort bookItemLoanRepository;
    private final BookItemRequestFacade bookItemRequestFacade;

    Page<BookItemLoan> execute(Long userId, BookItemLoanStatus status, Boolean renewable, Pageable pageable) {
        Page<BookItemLoan> resultPage = bookItemLoanRepository.findPageOfBookLoansByParams(userId, status, pageable);
        if (!Boolean.TRUE.equals(renewable)) {
            return resultPage;
        }
        Predicate<BookItemLoan> byRenewable = (loan) ->
                !loan.getDueDate().isBefore(LocalDate.now().atStartOfDay())
                && !bookItemRequestFacade.isBookItemRequested(loan.getBookItemId());
        List<BookItemLoan> filteredLoans = resultPage.getContent().stream()
                .filter(byRenewable)
                .toList();

        return new PageImpl<>(filteredLoans, pageable, resultPage.getTotalElements());
    }
}
