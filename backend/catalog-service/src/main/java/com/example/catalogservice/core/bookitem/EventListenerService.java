package com.example.catalogservice.core.bookitem;

import com.example.catalogservice.domain.model.LoanReturnDate;
import com.example.catalogservice.domain.model.bookitem.*;
import com.example.catalogservice.domain.ports.BookItemRepositoryPort;
import com.example.catalogservice.domain.ports.BookItemRequestServicePort;
import com.example.catalogservice.domain.ports.EventListenerPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
class EventListenerService implements EventListenerPort {
    private final BookItemRepositoryPort bookItemRepository;
    private final BookItemRequestServicePort bookItemRequestService;
    private final BookItemService bookItemService;

    @Override
    public void handleRequestCreatedEvent(BookItemId bookItemId) {
        bookItemService.findBookItemById(bookItemId).ifPresent(bookItem -> {
            if (bookItem.getStatus() == BookItemStatus.AVAILABLE) {
                bookItemRepository.updateStatus(bookItemId, BookItemStatus.REQUESTED);
                bookItemService.save(bookItem);
            }
        });
    }

    @Override
    public void handleRequestCanceledEvent(BookItemId bookItemId) {
        bookItemService.findBookItemById(bookItemId).ifPresent(bookItem -> {
            boolean bookIsRequested = bookItemRequestService.isBookItemRequested(bookItemId);
            boolean hasStatusRequested = bookItem.getStatus() == BookItemStatus.REQUESTED;
            if (!bookIsRequested && hasStatusRequested) {
                bookItem.setStatus(BookItemStatus.AVAILABLE);
                bookItemService.save(bookItem);
            }
        });
    }

    @Override
    public void handleLoanCreatedEvent(BookItemId bookItemId, LoanCreationDate creationDate, LoanDueDate dueDate) {
        bookItemService.findBookItemById(bookItemId).ifPresent(bookItem -> {
            bookItem.setStatus(BookItemStatus.LOANED);
            bookItem.setBorrowedDate(creationDate);
            bookItem.setDueDate(dueDate);
            bookItemService.save(bookItem);
        });
    }

    @Override
    public void handleLoanProlongedEvent(BookItemId bookItemId, LoanDueDate dueDate) {
        bookItemService.findBookItemById(bookItemId).ifPresent(bookItem -> {
            bookItem.setDueDate(dueDate);
            bookItemService.save(bookItem);
        });
    }

    @Override
    public void handleBookItemReturnedEvent(BookItemId bookItemId, LoanReturnDate dueDate) {
        bookItemService.findBookItemById(bookItemId).ifPresent(bookItem -> {
            boolean bookIsReserved = bookItemRequestService.isBookItemRequested(bookItemId);

            if (bookIsReserved) {
                bookItem.setStatus(BookItemStatus.REQUESTED);
            } else {
                bookItem.setStatus(BookItemStatus.AVAILABLE);
            }

            bookItem.setDueDate(new LoanDueDate(dueDate.value()));
            bookItemService.save(bookItem);
        });
    }

    @Override
    public void handleBookItemLostEvent(BookItemId bookItemId) {
        bookItemService.findBookItemById(bookItemId).ifPresent(bookItem -> {
            bookItem.setStatus(BookItemStatus.LOST);
            bookItem.setDueDate(null);
            bookItemService.save(bookItem);
        });
    }
}
