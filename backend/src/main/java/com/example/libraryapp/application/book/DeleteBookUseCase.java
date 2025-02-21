package com.example.libraryapp.application.book;

import com.example.libraryapp.domain.book.ports.BookRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DeleteBookUseCase {
    private final BookRepositoryPort bookRepository;

    void execute(Long id) {
        bookRepository.deleteById(id);
    }
}
