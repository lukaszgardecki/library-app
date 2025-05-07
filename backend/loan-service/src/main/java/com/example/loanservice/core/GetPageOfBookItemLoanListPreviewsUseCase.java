package com.example.loanservice.core;

import com.example.loanservice.domain.model.values.BookItemId;
import com.example.loanservice.domain.model.BookItemLoanListPreviewProjection;
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
class GetPageOfBookItemLoanListPreviewsUseCase {
    private final BookItemLoanRepositoryPort bookItemLoanRepository;
    private final BookItemRequestServicePort bookItemRequestService;

    public Page<BookItemLoanListPreviewProjection> execute(UserId userId, String query, LoanStatus status, Boolean renewable, Pageable pageable) {
        Page<BookItemLoanListPreviewProjection> resultPage = bookItemLoanRepository.findPageOfBookLoanListPreviews(userId, query, status, pageable);
        if (!Boolean.TRUE.equals(renewable)) {
            return resultPage;
        }
        Predicate<BookItemLoanListPreviewProjection> byRenewable = (loan) ->
                !loan.getDueDate().isBefore(LocalDate.now())
                && !bookItemRequestService.isBookItemRequested(new BookItemId(loan.getBookItemId()));
        List<BookItemLoanListPreviewProjection> filteredLoans = resultPage.getContent().stream()
                .filter(byRenewable)
                .toList();

        return new PageImpl<>(filteredLoans, pageable, resultPage.getTotalElements());
    }
}
