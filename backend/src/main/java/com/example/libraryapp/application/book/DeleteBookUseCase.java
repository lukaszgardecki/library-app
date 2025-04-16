package com.example.libraryapp.application.book;

import com.example.libraryapp.domain.book.model.BookId;
import com.example.libraryapp.domain.book.ports.BookRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DeleteBookUseCase {
    private final BookRepositoryPort bookRepository;

    void execute(BookId id) {
        bookRepository.deleteById(id);
    }
}
