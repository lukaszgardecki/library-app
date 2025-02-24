package com.example.libraryapp.infrastructure.persistence.inmemory;

import com.example.libraryapp.domain.book.model.Book;
import com.example.libraryapp.domain.book.ports.BookRepositoryPort;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryBookRepositoryAdapter implements BookRepositoryPort {
    private final ConcurrentHashMap<Long, Book> map = new ConcurrentHashMap<>();
    private static long id = 0;

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(++id);
        }
        return map.put(book.getId(), book);
    }

    @Override
    public void deleteById(Long id) {
        map.remove(id);
    }
}
