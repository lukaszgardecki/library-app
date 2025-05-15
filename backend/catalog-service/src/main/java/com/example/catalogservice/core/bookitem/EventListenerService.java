package com.example.catalogservice.core.bookitem;

import com.example.catalogservice.domain.model.bookitem.values.BookItemId;
import com.example.catalogservice.domain.model.bookitem.values.BookItemStatus;
import com.example.catalogservice.domain.model.bookitem.values.LoanCreationDate;
import com.example.catalogservice.domain.model.bookitem.values.LoanDueDate;
import com.example.catalogservice.domain.ports.in.EventListenerPort;
import com.example.catalogservice.domain.ports.out.BookItemRepositoryPort;
import com.example.catalogservice.domain.ports.out.BookItemRequestServicePort;
import lombok.RequiredArgsConstructor;

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
    public void handleBookItemReturnedEvent(BookItemId bookItemId, LoanDueDate dueDate) {
        bookItemService.findBookItemById(bookItemId).ifPresent(bookItem -> {
            boolean bookIsReserved = bookItemRequestService.isBookItemRequested(bookItemId);

            if (bookIsReserved) {
                bookItem.setStatus(BookItemStatus.REQUESTED);
            } else {
                bookItem.setStatus(BookItemStatus.AVAILABLE);
            }

            bookItem.setDueDate(dueDate);
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
