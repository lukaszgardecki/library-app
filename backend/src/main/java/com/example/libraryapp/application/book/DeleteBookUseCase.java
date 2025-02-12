package com.example.libraryapp.application.book;

import com.example.libraryapp.domain.book.ports.BookRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DeleteBookUseCase {
    private final BookRepository bookRepository;

    void execute(Long id) {
        bookRepository.deleteById(id);
    }
}
