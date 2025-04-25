package com.example.loanservice.core;

import com.example.loanservice.domain.dto.BookItemDto;
import com.example.loanservice.domain.model.*;
import com.example.loanservice.domain.ports.CatalogServicePort;
import com.example.loanservice.domain.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class ProcessLostBookItemUseCase {
//    private final AuthenticationFacade authFacade;
    private final CatalogServicePort catalogService;
    private final BookItemLoanService bookItemLoanService;
    private final EventPublisherPort publisher;

    void execute(BookItemId bookItemId, UserId userId) {
//        authFacade.validateAdminAccess();
        BookItemLoan bookItemLoan = bookItemLoanService.getBookItemLoan(bookItemId, userId, LoanStatus.CURRENT);
        bookItemLoan.setReturnDate(new LoanReturnDate(LocalDateTime.now()));
        bookItemLoan.setStatus(LoanStatus.COMPLETED);
        bookItemLoanService.save(bookItemLoan);
        BookItemDto bookItem = catalogService.getBookItemById(bookItemId);
        Price bookItemPrice = new Price(bookItem.getPrice());
        publisher.publishBookItemLostEvent(BookItemLoanMapper.toDto(bookItemLoan), bookItemPrice);
    }
}
