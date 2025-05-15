package com.example.catalogservice.infrastructure.http;

import com.example.catalogservice.core.book.BookFacade;
import com.example.catalogservice.domain.integration.warehouse.rack.Rack;
import com.example.catalogservice.domain.integration.warehouse.shelf.Shelf;
import com.example.catalogservice.domain.model.book.Book;
import com.example.catalogservice.domain.model.book.values.BookId;
import com.example.catalogservice.domain.model.bookitem.BookItem;
import com.example.catalogservice.domain.ports.out.WarehouseServicePort;
import com.example.catalogservice.infrastructure.http.dto.BookDto;
import com.example.catalogservice.infrastructure.http.dto.BookItemWithBookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class DetailsAggregator {
    private final BookFacade bookFacade;
    private final WarehouseServicePort warehouseService;

    Page<BookItemWithBookDto> getBookItemWithDetails(Page<BookItem> bookItems) {

        List<BookId> ids = bookItems.map(BookItem::getBookId).toList();
        List<Book> books = bookFacade.getBooksByIds(ids);
        Map<Long, Book> bookMap = books.stream()
                .collect(Collectors.toMap(book -> book.getId().value(), Function.identity()));
        return bookItems.map(bookItem -> {
            BookDto book = BookMapper.toDto(bookMap.get(bookItem.getBookId().value()));
            Rack rack = warehouseService.getRackById(bookItem.getRackId());
            Shelf shelf = warehouseService.getShelfById(bookItem.getShelfId());
            return new BookItemWithBookDto(
                    bookItem.getId().value(),
                    bookItem.getBarcode().value(),
                    bookItem.getIsReferenceOnly().value(),
                    bookItem.getBorrowedDate().value() != null ? bookItem.getBorrowedDate().value().toLocalDate() : null,
                    bookItem.getDueDate().value() != null ? bookItem.getDueDate().value() : null,
                    bookItem.getPrice().value(),
                    bookItem.getStatus(),
                    bookItem.getDateOfPurchase().value(),
                    book,
                    bookItem.getRackId().value(),
                    rack.getName().value(),
                    bookItem.getShelfId().value(),
                    shelf.getName().value()
            );
        });
    }
}
