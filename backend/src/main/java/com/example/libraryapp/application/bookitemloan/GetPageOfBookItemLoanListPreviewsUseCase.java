package com.example.libraryapp.application.bookitemloan;

import com.example.libraryapp.application.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.domain.bookitemloan.model.BookItemLoanListPreviewProjection;
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
class GetPageOfBookItemLoanListPreviewsUseCase {
    private final BookItemLoanRepositoryPort bookItemLoanRepository;
    private final BookItemRequestFacade bookItemRequestFacade;

    public Page<BookItemLoanListPreviewProjection> execute(Long userId, String query, BookItemLoanStatus status, Boolean renewable, Pageable pageable) {
        Page<BookItemLoanListPreviewProjection> resultPage = bookItemLoanRepository.findPageOfBookLoanListPreviews(userId, query, status, pageable);
        if (!Boolean.TRUE.equals(renewable)) {
            return resultPage;
        }
        Predicate<BookItemLoanListPreviewProjection> byRenewable = (loan) ->
                !loan.getDueDate().isBefore(LocalDate.now())
                && !bookItemRequestFacade.isBookItemRequested(loan.getBookItemId());
        List<BookItemLoanListPreviewProjection> filteredLoans = resultPage.getContent().stream()
                .filter(byRenewable)
                .toList();

        return new PageImpl<>(filteredLoans, pageable, resultPage.getTotalElements());
    }
}
