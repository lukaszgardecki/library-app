package com.example.catalogservice.bookitem.core;

import com.example.catalogservice.bookitem.domain.exceptions.BookItemNotFoundException;
import com.example.catalogservice.bookitem.domain.model.BookItem;
import com.example.catalogservice.bookitem.domain.model.BookItemId;
import com.example.catalogservice.bookitem.domain.ports.BookItemRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetBookItemUseCase {
    private final BookItemRepositoryPort bookItemRepository;

    BookItem execute(BookItemId id) {
        return bookItemRepository.findById(id).orElseThrow(() -> new BookItemNotFoundException(id));
    }
}
