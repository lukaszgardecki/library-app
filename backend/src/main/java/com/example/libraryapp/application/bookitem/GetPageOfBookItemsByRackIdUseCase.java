package com.example.libraryapp.application.bookitem;

import com.example.libraryapp.domain.bookitem.model.BookItem;
import com.example.libraryapp.domain.bookitem.ports.BookItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
class GetPageOfBookItemsByRackIdUseCase {
    private final BookItemRepository bookItemRepository;

    Page<BookItem> execute(Long rackId, Pageable pageable) {
        return bookItemRepository.findAllByRackId(rackId, pageable);
    }
}
