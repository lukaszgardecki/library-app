package com.example.libraryapp.core.bookitem;

import com.example.libraryapp.domain.bookitem.ports.BookItemRepositoryPort;
import com.example.libraryapp.domain.rack.model.RackId;
import com.example.libraryapp.domain.shelf.model.ShelfId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CountByParamsUseCase {
    private final BookItemRepositoryPort bookItemRepository;

    Long execute(RackId rackId, ShelfId shelfId) {
        return bookItemRepository.countByParams(rackId, shelfId);
    }
}
