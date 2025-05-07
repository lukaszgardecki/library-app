package com.example.catalogservice.core.bookitem;

import com.example.catalogservice.core.book.BookFacade;
import com.example.catalogservice.domain.dto.BookDto;
import com.example.catalogservice.domain.dto.BookItemWithBookDto;
import com.example.catalogservice.domain.integration.warehouse.dto.RackDto;
import com.example.catalogservice.domain.integration.warehouse.dto.ShelfDto;
import com.example.catalogservice.domain.exception.BookItemNotFoundException;
import com.example.catalogservice.domain.model.book.values.BookId;
import com.example.catalogservice.domain.model.book.values.Title;
import com.example.catalogservice.domain.model.bookitem.*;
import com.example.catalogservice.domain.model.bookitem.values.BookItemBarcode;
import com.example.catalogservice.domain.model.bookitem.values.BookItemId;
import com.example.catalogservice.domain.model.bookitem.values.RackId;
import com.example.catalogservice.domain.model.bookitem.values.ShelfId;
import com.example.catalogservice.domain.ports.out.BookItemRepositoryPort;
import com.example.catalogservice.domain.ports.out.WarehouseServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class BookItemService {
    private final BookItemRepositoryPort bookItemRepository;
    private final WarehouseServicePort warehouseService;
    private final BookItemBarcodeGenerator generator;
    private final BookFacade bookFacade;

    Page<BookItemWithBookDto> getAllByParams(BookId bookId, RackId rackId, ShelfId shelfId, String query, Pageable pageable) {
        Page<BookItem> bookItems = bookItemRepository.findAllByParams(bookId, rackId, shelfId, query, pageable);
        List<BookId> ids = bookItems.map(BookItem::getBookId).toList();
        List<BookDto> books = bookFacade.getBooksByIds(ids);
        Map<Long, BookDto> bookMap = books.stream()
                .collect(Collectors.toMap(BookDto::getId, Function.identity()));
        return bookItems.map(bookItem -> {
            BookDto bookDto = bookMap.get(bookItem.getBookId().value());
            RackDto rack = warehouseService.getRackById(bookItem.getRackId());
            ShelfDto shelf = warehouseService.getShelfById(bookItem.getShelfId());
            return BookItemMapper.toDto(bookItem, bookDto, rack, shelf);
        });
    }

    BookItem getBookItemById(BookItemId id) {
        return bookItemRepository.findById(id)
                .orElseThrow(() -> new BookItemNotFoundException(id));
    }

    Optional<BookItem> findBookItemById(BookItemId id) {
        return bookItemRepository.findById(id);
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
}
