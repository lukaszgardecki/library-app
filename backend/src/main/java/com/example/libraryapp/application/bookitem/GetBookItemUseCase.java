package com.example.libraryapp.application.bookitem;

import com.example.libraryapp.domain.bookitem.exceptions.BookItemNotFoundException;
import com.example.libraryapp.domain.bookitem.model.BookItem;
import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.bookitem.ports.BookItemRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetBookItemUseCase {
    private final BookItemRepositoryPort bookItemRepository;

    BookItem execute(BookItemId id) {
        return bookItemRepository.findById(id).orElseThrow(() -> new BookItemNotFoundException(id));
    }
}
