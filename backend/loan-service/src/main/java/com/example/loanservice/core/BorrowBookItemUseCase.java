package com.example.loanservice.core;

import com.example.loanservice.domain.dto.BookItemDto;
import com.example.loanservice.domain.model.*;
import com.example.loanservice.domain.ports.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class BorrowBookItemUseCase {
    private final UserServicePort userService;
//    private final AuthenticationFacade authFacade;
    private final BookItemRequestServicePort bookItemRequestService;
    private final CatalogServicePort catalogService;
    private final BookItemLoanService bookItemLoanService;
    private final FineServicePort fineService;
    private final EventPublisherPort publisher;

    BookItemLoan execute(BookItemId bookItemId, UserId userId) {
//        authFacade.validateOwnerOrAdminAccess(userId);
        userService.verifyUserForBookItemLoan(userId);
        fineService.verifyUserForFines(userId);
        BookItemDto bookItem = catalogService.verifyAndGetBookItemForLoan(bookItemId);
        RequestId requestId = bookItemRequestService.checkIfBookItemRequestStatusIsReady(bookItemId, userId);
        BookItemLoan bookItemLoan = bookItemLoanService.saveLoan(bookItemId, userId, new BookId(bookItem.getBookId()));
        publisher.publishBookItemLoanedEvent(BookItemLoanMapper.toDto(bookItemLoan), requestId);
        return bookItemLoan;
    }
}
