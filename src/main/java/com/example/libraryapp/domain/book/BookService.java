package com.example.libraryapp.domain.book;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Optional<BookDto> findBookById(Long id) {
        return bookRepository.findById(id)
                .map(BookDtoMapper::map);
    }
}
