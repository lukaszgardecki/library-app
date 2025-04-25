package com.example.catalogservice.core.bookitem;

import com.example.catalogservice.domain.model.LoanReturnDate;
import com.example.catalogservice.domain.model.bookitem.*;
import com.example.catalogservice.domain.ports.BookItemRepositoryPort;
import com.example.catalogservice.domain.ports.BookItemRequestServicePort;
import com.example.catalogservice.domain.ports.EventListenerPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class EventListenerService implements EventListenerPort {
    private final BookItemRepositoryPort bookItemRepository;
    private final BookItemRequestServicePort bookItemRequestService;
    private final BookItemService bookItemService;

    @Override
    public void handleRequestCreatedEvent(BookItemId bookItemId) {
        BookItem bookItem = bookItemService.getBookItemById(bookItemId);
        if (bookItem.getStatus() == BookItemStatus.AVAILABLE) {
            bookItemRepository.updateStatus(bookItemId, BookItemStatus.REQUESTED);
        }
    }

    @Override
    public void handleRequestCanceledEvent(BookItemId bookItemId) {
        BookItem bookItem = bookItemService.getBookItemById(bookItemId);
        boolean bookIsRequested = bookItemRequestService.isBookItemRequested(bookItemId);
        boolean hasStatusRequested = bookItem.getStatus() == BookItemStatus.REQUESTED;
        if (!bookIsRequested && hasStatusRequested) {
            bookItem.setStatus(BookItemStatus.AVAILABLE);
        }
    }

    @Override
    public void handleLoanCreatedEvent(BookItemId bookItemId, LoanCreationDate creationDate, LoanDueDate dueDate) {
        BookItem bookItem = bookItemService.getBookItemById(bookItemId);
        bookItem.setStatus(BookItemStatus.LOANED);
        bookItem.setBorrowedDate(creationDate);
        bookItem.setDueDate(dueDate);
        bookItemService.save(bookItem);
    }

    @Override
    public void handleLoanProlongedEvent(BookItemId bookItemId, LoanDueDate dueDate) {
        BookItem bookItem = bookItemService.getBookItemById(bookItemId);
        bookItem.setDueDate(dueDate);
        bookItemService.save(bookItem);
    }

    @Override
    public void handleBookItemReturnedEvent(BookItemId bookItemId, LoanReturnDate dueDate) {
        BookItem bookItem = bookItemService.getBookItemById(bookItemId);
        boolean bookIsReserved = bookItemRequestService.isBookItemRequested(bookItemId);

        if (bookIsReserved) {
            bookItem.setStatus(BookItemStatus.REQUESTED);
        } else {
            bookItem.setStatus(BookItemStatus.AVAILABLE);
        }

        bookItem.setDueDate(new LoanDueDate(dueDate.value()));
        bookItemService.save(bookItem);
    }

    @Override
    public void handleBookItemLostEvent(BookItemId bookItemId) {
        BookItem bookItem = bookItemService.getBookItemById(bookItemId);
        bookItem.setStatus(BookItemStatus.LOST);
        bookItem.setDueDate(null);
        bookItemService.save(bookItem);
    }
}
