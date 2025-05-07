package com.example.catalogservice.core.bookitem;

import com.example.catalogservice.domain.model.book.values.BookId;
import com.example.catalogservice.domain.dto.BookItemWithBookDto;
import com.example.catalogservice.domain.model.bookitem.values.RackId;
import com.example.catalogservice.domain.model.bookitem.values.ShelfId;
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
