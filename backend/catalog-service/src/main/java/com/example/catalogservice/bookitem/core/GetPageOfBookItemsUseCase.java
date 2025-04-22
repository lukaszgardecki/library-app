package com.example.catalogservice.bookitem.core;

import com.example.catalogservice.book.domain.model.BookId;
import com.example.catalogservice.bookitem.domain.dto.BookItemWithBookDto;
import com.example.catalogservice.bookitem.domain.model.RackId;
import com.example.catalogservice.bookitem.domain.model.ShelfId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
class GetPageOfBookItemsUseCase {
    private final BookItemService bookItemService;

    Page<BookItemWithBookDto> execute(BookId bookId, RackId rackId, ShelfId shelfId, String query, Pageable pageable) {
        return bookItemService.getAllByParams(bookId, rackId, shelfId, query, pageable);
    }
}
