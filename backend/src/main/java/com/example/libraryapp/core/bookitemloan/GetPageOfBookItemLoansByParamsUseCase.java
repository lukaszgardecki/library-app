package com.example.libraryapp.core.bookitemloan;

import com.example.libraryapp.core.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.domain.bookitemloan.model.BookItemLoan;
import com.example.libraryapp.domain.bookitemloan.model.LoanStatus;
import com.example.libraryapp.domain.bookitemloan.ports.BookItemLoanRepositoryPort;
import com.example.libraryapp.domain.user.model.UserId;
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

    Page<BookItemLoan> execute(UserId userId, LoanStatus status, Boolean renewable, Pageable pageable) {
        Page<BookItemLoan> resultPage = bookItemLoanRepository.findPageOfBookLoansByParams(userId, status, pageable);
        if (!Boolean.TRUE.equals(renewable)) {
            return resultPage;
        }
        Predicate<BookItemLoan> byRenewable = (loan) ->
                !loan.getDueDate().value().isBefore(LocalDate.now().atStartOfDay())
                && !bookItemRequestFacade.isBookItemRequested(loan.getBookItemId());
        List<BookItemLoan> filteredLoans = resultPage.getContent().stream()
                .filter(byRenewable)
                .toList();

        return new PageImpl<>(filteredLoans, pageable, resultPage.getTotalElements());
    }
}
