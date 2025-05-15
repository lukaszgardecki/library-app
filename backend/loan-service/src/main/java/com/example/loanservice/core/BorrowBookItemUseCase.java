package com.example.loanservice.core;

import com.example.loanservice.domain.integration.catalogservice.bookitem.BookItem;
import com.example.loanservice.domain.integration.requestservice.RequestId;
import com.example.loanservice.domain.model.BookItemLoan;
import com.example.loanservice.domain.model.values.BookItemId;
import com.example.loanservice.domain.model.values.UserId;
import com.example.loanservice.domain.ports.out.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class BorrowBookItemUseCase {
    private final UserServicePort userService;
    private final BookItemRequestServicePort bookItemRequestService;
    private final CatalogServicePort catalogService;
    private final BookItemLoanService bookItemLoanService;
    private final FineServicePort fineService;
    private final EventPublisherPort publisher;

    BookItemLoan execute(BookItemId bookItemId, UserId userId) {
        userService.verifyUserForBookItemLoan(userId);
        fineService.verifyUserForFines(userId);
        BookItem bookItem = catalogService.verifyAndGetBookItemForLoan(bookItemId);
        RequestId requestId = bookItemRequestService.checkIfBookItemRequestStatusIsReady(bookItemId, userId);
        BookItemLoan bookItemLoan = bookItemLoanService.saveLoan(bookItemId, userId, bookItem.getBookId());
        publisher.publishLoanCreatedEvent(bookItemLoan, requestId, bookItem.getIsReferenceOnly());
        return bookItemLoan;
    }
}
