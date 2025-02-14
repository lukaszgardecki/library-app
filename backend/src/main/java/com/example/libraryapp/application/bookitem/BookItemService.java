package com.example.libraryapp.application.bookitem;

import com.example.libraryapp.application.book.BookFacade;
import com.example.libraryapp.application.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.domain.bookitem.exceptions.BookItemNotFoundException;
import com.example.libraryapp.domain.bookitem.model.BookItem;
import com.example.libraryapp.domain.bookitem.model.BookItemStatus;
import com.example.libraryapp.domain.bookitem.ports.BookItemRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class BookItemService {
    private final BookItemRepository bookItemRepository;
    private final BookItemRequestFacade bookItemRequestFacade;
    private final BookItemBarcodeGenerator generator;
    private final BookFacade bookFacade;

    BookItem getBookItemById(Long id) {
        return bookItemRepository.findById(id)
                .orElseThrow(() -> new BookItemNotFoundException(id));
    }

    String getBookTitleByBookId(Long bookId) {
        return bookFacade.getBook(bookId).getTitle();
    }

    BookItem save(BookItem bookItem) {
        return bookItemRepository.save(bookItem);
    }

    String generateBarcode(Long bookItemId) {
        return generator.generateBarcode(bookItemId);
    }

    void updateBookItemOnRequest(Long bookItemId) {
        BookItem bookItem = getBookItemById(bookItemId);
        if (bookItem.getStatus() == BookItemStatus.AVAILABLE) {
            bookItemRepository.updateStatus(bookItemId, BookItemStatus.REQUESTED);
        }
    }

    void updateBookItemOnRequestCancellation(Long bookItemId) {
        BookItem bookItem = getBookItemById(bookItemId);
        boolean bookIsRequested = bookItemRequestFacade.isBookItemRequested(bookItemId);
        boolean hasStatusRequested = bookItem.getStatus() == BookItemStatus.REQUESTED;
        if (!bookIsRequested && hasStatusRequested) {
            bookItem.setStatus(BookItemStatus.AVAILABLE);
        }
    }

    void updateBookItemOnReturn(Long bookItemId, LocalDateTime dueDate) {
        BookItem bookItem = getBookItemById(bookItemId);
        boolean bookIsReserved = bookItemRequestFacade.isBookItemRequested(bookItemId);

        if (bookIsReserved) {
            bookItem.setStatus(BookItemStatus.REQUESTED);
        } else {
            bookItem.setStatus(BookItemStatus.AVAILABLE);
        }

        bookItem.setDueDate(dueDate.toLocalDate());
        save(bookItem);
    }

    void updateBookItemOnLoss(Long bookItemId) {
        BookItem bookItem = getBookItemById(bookItemId);
        bookItem.setStatus(BookItemStatus.LOST);
        bookItem.setDueDate(null);
        save(bookItem);
    }

    void updateBookItemOnRenewal(Long bookItemId, LocalDateTime dueDate) {
        BookItem bookItem = getBookItemById(bookItemId);
        bookItem.setDueDate(dueDate.toLocalDate());
        save(bookItem);
    }

    void updateBookItemOnLoan(Long bookItemId, LocalDateTime creationDate, LocalDateTime dueDate) {
        BookItem bookItem = getBookItemById(bookItemId);
        bookItem.setStatus(BookItemStatus.LOANED);
        bookItem.setBorrowed(creationDate.toLocalDate());
        bookItem.setDueDate(dueDate.toLocalDate());
        save(bookItem);
    }
}
