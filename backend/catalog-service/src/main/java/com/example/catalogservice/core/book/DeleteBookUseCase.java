package com.example.catalogservice.core.book;

import com.example.catalogservice.domain.model.book.BookId;
import com.example.catalogservice.domain.ports.BookRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DeleteBookUseCase {
    private final BookRepositoryPort bookRepository;

    void execute(BookId id) {
        bookRepository.deleteById(id);
    }
}
