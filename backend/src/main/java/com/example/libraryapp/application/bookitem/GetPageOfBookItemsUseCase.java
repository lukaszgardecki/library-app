package com.example.libraryapp.application.bookitem;

import com.example.libraryapp.domain.book.model.BookId;
import com.example.libraryapp.domain.bookitem.dto.BookItemWithBookDto;
import com.example.libraryapp.domain.rack.model.RackId;
import com.example.libraryapp.domain.shelf.model.ShelfId;
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
