package com.example.catalogservice.core.bookitem;

import com.example.catalogservice.domain.model.bookitem.values.RackId;
import com.example.catalogservice.domain.model.bookitem.values.ShelfId;
import com.example.catalogservice.domain.ports.out.BookItemRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CountByParamsUseCase {
    private final BookItemRepositoryPort bookItemRepository;

    Long execute(RackId rackId, ShelfId shelfId) {
        return bookItemRepository.countByParams(rackId, shelfId);
    }
}
