package com.example.libraryapp.application.bookitem;

import com.example.libraryapp.domain.book.model.BookId;
import com.example.libraryapp.domain.bookitem.model.BookItem;
import com.example.libraryapp.domain.bookitem.ports.BookItemRepositoryPort;
import com.example.libraryapp.domain.rack.model.RackId;
import com.example.libraryapp.domain.shelf.model.ShelfId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
class GetPageOfBookItemsUseCase {
    private final BookItemRepositoryPort bookItemRepository;

    Page<BookItem> execute(BookId bookId, RackId rackId, ShelfId shelfId, Pageable pageable) {
        return bookItemRepository.findAllByParams(bookId, rackId, shelfId, pageable);
    }
}
