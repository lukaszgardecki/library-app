package com.example.libraryapp.infrastructure.persistence.inmemory;

import com.example.libraryapp.domain.book.model.Book;
import com.example.libraryapp.domain.book.model.BookId;
import com.example.libraryapp.domain.book.ports.BookRepositoryPort;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryBookRepositoryAdapter implements BookRepositoryPort {
    private final ConcurrentHashMap<BookId, Book> map = new ConcurrentHashMap<>();
    private static long id = 0;

    @Override
    public List<Book> findAllById(List<BookId> ids) {
        return map.values().stream()
                .filter(book -> ids.stream().anyMatch(id -> id.equals(book.getId())))
                .toList();
    }

    @Override
    public Optional<Book> findById(BookId id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(new BookId(++id));
        }
        return map.put(book.getId(), book);
    }

    @Override
    public void deleteById(BookId id) {
        map.remove(id);
    }
}
