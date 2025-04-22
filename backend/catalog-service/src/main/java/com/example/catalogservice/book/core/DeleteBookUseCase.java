package com.example.catalogservice.book.core;

import com.example.catalogservice.book.domain.model.BookId;
import com.example.catalogservice.book.domain.ports.BookRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DeleteBookUseCase {
    private final BookRepositoryPort bookRepository;

    void execute(BookId id) {
        bookRepository.deleteById(id);
    }
}
