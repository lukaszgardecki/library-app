package com.example.libraryapp.application.bookitem;

import com.example.libraryapp.domain.bookitem.model.BookItem;
import com.example.libraryapp.domain.bookitem.ports.BookItemRepositoryPort;
import com.example.libraryapp.domain.rack.model.RackId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
class GetPageOfBookItemsByRackIdUseCase {
    private final BookItemRepositoryPort bookItemRepository;

    Page<BookItem> execute(RackId rackId, Pageable pageable) {
        return bookItemRepository.findAllByRackId(rackId, pageable);
    }
}
