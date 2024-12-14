package com.example.libraryapp.NEWapplication.bookitem;

import com.example.libraryapp.NEWdomain.bookitem.model.BookItem;
import com.example.libraryapp.NEWdomain.bookitem.ports.BookItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
class GetPageOfBookItemsByBookIdUseCase {
    private final BookItemRepository bookItemRepository;

    Page<BookItem> execute(Long bookId, Pageable pageable) {
        return bookItemRepository.getPageOfBookItemsByBookId(bookId, pageable);
    }
}
