package com.example.catalogservice.core.bookitem;

import com.example.catalogservice.core.book.BookFacade;
import com.example.catalogservice.domain.exception.BookItemNotFoundException;
import com.example.catalogservice.domain.model.book.values.BookId;
import com.example.catalogservice.domain.model.book.values.Title;
import com.example.catalogservice.domain.model.bookitem.BookItem;
import com.example.catalogservice.domain.model.bookitem.values.BookItemBarcode;
import com.example.catalogservice.domain.model.bookitem.values.BookItemId;
import com.example.catalogservice.domain.model.bookitem.values.RackId;
import com.example.catalogservice.domain.model.bookitem.values.ShelfId;
import com.example.catalogservice.domain.ports.out.BookItemRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@RequiredArgsConstructor
class BookItemService {
    private final BookItemRepositoryPort bookItemRepository;
    private final BookItemBarcodeGenerator generator;
    private final BookFacade bookFacade;

    Page<BookItem> getAllByParams(BookId bookId, RackId rackId, ShelfId shelfId, String query, Pageable pageable) {
        return bookItemRepository.findAllByParams(bookId, rackId, shelfId, query, pageable);
    }

    BookItem getBookItemById(BookItemId id) {
        return bookItemRepository.findById(id)
                .orElseThrow(() -> new BookItemNotFoundException(id));
    }

    Optional<BookItem> findBookItemById(BookItemId id) {
        return bookItemRepository.findById(id);
    }

    Title getBookTitleByBookId(BookId bookId) {
        return new Title(bookFacade.getBook(bookId).getTitle().value());
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
}
