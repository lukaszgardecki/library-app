package com.example.libraryapp.application.bookitem;

import com.example.libraryapp.application.book.BookFacade;
import com.example.libraryapp.application.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.domain.book.model.BookId;
import com.example.libraryapp.domain.book.model.Title;
import com.example.libraryapp.domain.bookitem.exceptions.BookItemNotFoundException;
import com.example.libraryapp.domain.bookitem.model.BookItem;
import com.example.libraryapp.domain.bookitem.model.BookItemBarcode;
import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.bookitem.model.BookItemStatus;
import com.example.libraryapp.domain.bookitem.ports.BookItemRepositoryPort;
import com.example.libraryapp.domain.bookitemloan.model.LoanCreationDate;
import com.example.libraryapp.domain.bookitemloan.model.LoanDueDate;
import com.example.libraryapp.domain.bookitemloan.model.LoanReturnDate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class BookItemService {
    private final BookItemRepositoryPort bookItemRepository;
    private final BookItemRequestFacade bookItemRequestFacade;
    private final BookItemBarcodeGenerator generator;
    private final BookFacade bookFacade;

    BookItem getBookItemById(BookItemId id) {
        return bookItemRepository.findById(id)
                .orElseThrow(() -> new BookItemNotFoundException(id));
    }

    Title getBookTitleByBookId(BookId bookId) {
        return new Title(bookFacade.getBook(bookId).getTitle());
    }

    BookItem save(BookItem bookItem) {
        return bookItemRepository.save(bookItem);
    }

    BookItemBarcode generateNewBookItemBarcode(BookItemId bookItemId) {
        return generator.generateBarcode(bookItemId);
    }

    void updateBookItemBarcode(BookItemId bookItemId, BookItemBarcode barcode) {
        bookItemRepository.updateBarcode(bookItemId, barcode);
    }

    void updateBookItemOnRequest(BookItemId bookItemId) {
        BookItem bookItem = getBookItemById(bookItemId);
        if (bookItem.getStatus() == BookItemStatus.AVAILABLE) {
            bookItemRepository.updateStatus(bookItemId, BookItemStatus.REQUESTED);
        }
    }

    void updateBookItemOnRequestCancellation(BookItemId bookItemId) {
        BookItem bookItem = getBookItemById(bookItemId);
        boolean bookIsRequested = bookItemRequestFacade.isBookItemRequested(bookItemId);
        boolean hasStatusRequested = bookItem.getStatus() == BookItemStatus.REQUESTED;
        if (!bookIsRequested && hasStatusRequested) {
            bookItem.setStatus(BookItemStatus.AVAILABLE);
        }
    }

    void updateBookItemOnReturn(BookItemId bookItemId, LoanReturnDate dueDate) {
        BookItem bookItem = getBookItemById(bookItemId);
        boolean bookIsReserved = bookItemRequestFacade.isBookItemRequested(bookItemId);

        if (bookIsReserved) {
            bookItem.setStatus(BookItemStatus.REQUESTED);
        } else {
            bookItem.setStatus(BookItemStatus.AVAILABLE);
        }

        bookItem.setDueDate(new LoanDueDate(dueDate.value()));
        save(bookItem);
    }

    void updateBookItemOnLoss(BookItemId bookItemId) {
        BookItem bookItem = getBookItemById(bookItemId);
        bookItem.setStatus(BookItemStatus.LOST);
        bookItem.setDueDate(null);
        save(bookItem);
    }

    void updateBookItemOnRenewal(BookItemId bookItemId, LoanDueDate dueDate) {
        BookItem bookItem = getBookItemById(bookItemId);
        bookItem.setDueDate(dueDate);
        save(bookItem);
    }

    void updateBookItemOnLoan(BookItemId bookItemId, LoanCreationDate creationDate, LoanDueDate dueDate) {
        BookItem bookItem = getBookItemById(bookItemId);
        bookItem.setStatus(BookItemStatus.LOANED);
        bookItem.setBorrowedDate(creationDate);
        bookItem.setDueDate(dueDate);
        save(bookItem);
    }
}
