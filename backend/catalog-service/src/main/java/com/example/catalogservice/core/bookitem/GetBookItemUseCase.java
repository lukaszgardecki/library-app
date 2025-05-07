package com.example.catalogservice.core.bookitem;

import com.example.catalogservice.domain.exception.BookItemNotFoundException;
import com.example.catalogservice.domain.model.bookitem.BookItem;
import com.example.catalogservice.domain.model.bookitem.values.BookItemId;
import com.example.catalogservice.domain.ports.out.BookItemRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetBookItemUseCase {
    private final BookItemRepositoryPort bookItemRepository;

    BookItem execute(BookItemId id) {
        return bookItemRepository.findById(id).orElseThrow(() -> new BookItemNotFoundException(id));
    }
}
