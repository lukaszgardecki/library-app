package com.example.libraryapp.NEWapplication.bookitem;

import com.example.libraryapp.NEWdomain.bookitem.exceptions.BookItemNotFoundException;
import com.example.libraryapp.NEWdomain.bookitem.model.BookItem;
import com.example.libraryapp.NEWdomain.bookitem.model.BookItemStatus;
import com.example.libraryapp.NEWdomain.bookitem.ports.BookItemRepository;
import com.example.libraryapp.NEWdomain.bookitemrequest.exceptions.BookItemRequestException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class BookItemService {
    private final BookItemRepository bookItemRepository;
    private final BookItemBarcodeGenerator generator;

    BookItem findBookItem(Long id) {
        return bookItemRepository.findById(id)
                .orElseThrow(() -> new BookItemNotFoundException(id));
    }

    BookItem saveBookItem(BookItem bookItem) {
        return bookItemRepository.save(bookItem);
    }

    void updateBookItemStatus(Long bookItemId, BookItemStatus status) {
        bookItemRepository.updateStatus(bookItemId, status);
    }

    String generateBarcode(Long bookItemId) {
        return generator.generateBarcode(bookItemId);
    }
}
