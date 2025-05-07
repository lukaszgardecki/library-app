package com.example.loanservice.core;

import com.example.loanservice.domain.model.BookItemLoan;
import com.example.loanservice.domain.model.values.LoanStatus;
import com.example.loanservice.domain.model.values.UserId;
import com.example.loanservice.domain.ports.out.BookItemLoanRepositoryPort;
import com.example.loanservice.domain.ports.out.BookItemRequestServicePort;
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
    private final BookItemRequestServicePort bookItemRequestService;

    Page<BookItemLoan> execute(UserId userId, LoanStatus status, Boolean renewable, Pageable pageable) {
        Page<BookItemLoan> resultPage = bookItemLoanRepository.findPageOfBookLoansByParams(userId, status, pageable);
        if (!Boolean.TRUE.equals(renewable)) {
            return resultPage;
        }
        Predicate<BookItemLoan> byRenewable = (loan) ->
                !loan.getDueDate().value().isBefore(LocalDate.now().atStartOfDay())
                && !bookItemRequestService.isBookItemRequested(loan.getBookItemId());
        List<BookItemLoan> filteredLoans = resultPage.getContent().stream()
                .filter(byRenewable)
                .toList();

        return new PageImpl<>(filteredLoans, pageable, resultPage.getTotalElements());
    }
}
