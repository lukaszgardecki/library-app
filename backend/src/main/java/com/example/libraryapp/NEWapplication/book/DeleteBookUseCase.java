package com.example.libraryapp.NEWapplication.book;

import com.example.libraryapp.NEWdomain.book.ports.BookRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DeleteBookUseCase {
    private final BookRepository bookRepository;

    void execute(Long id) {
        bookRepository.deleteById(id);
    }
}
