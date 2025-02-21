package com.example.libraryapp.infrastructure.persistence.inmemory;

import com.example.libraryapp.domain.bookitem.model.BookItem;
import com.example.libraryapp.domain.bookitem.model.BookItemStatus;
import com.example.libraryapp.domain.bookitem.ports.BookItemRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

public class InMemoryBookItemRepositoryAdapter implements BookItemRepositoryPort {
    private final ConcurrentHashMap<Long, BookItem> map = new ConcurrentHashMap<>();
    private static long id = 0;

    @Override
    public Optional<BookItem> findById(Long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Page<BookItem> findAll(Pageable pageable) {
        List<BookItem> allItems = new ArrayList<>(map.values());
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), allItems.size());
        List<BookItem> pagedItems = allItems.subList(start, end);
        return new PageImpl<>(pagedItems, pageable, allItems.size());
    }

    @Override
    public Page<BookItem> findAllByBookId(Long bookId, Pageable pageable) {
        List<BookItem> filteredItems = map.values().stream()
                .filter(bookItem -> bookItem.getBookId().equals(bookId))
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filteredItems.size());
        List<BookItem> pagedItems = filteredItems.subList(start, end);
        return new PageImpl<>(pagedItems, pageable, filteredItems.size());
    }

    @Override
    public Page<BookItem> findAllByRackId(Long rackId, Pageable pageable) {
        List<BookItem> filteredItems = map.values().stream()
                .filter(bookItem -> bookItem.getRackId().equals(rackId))
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filteredItems.size());
        List<BookItem> pagedItems = filteredItems.subList(start, end);
        return new PageImpl<>(pagedItems, pageable, filteredItems.size());
    }

    @Override
    public BookItem save(BookItem bookItem) {
        requireNonNull(bookItem);
        if (bookItem.getId() == null) {
            bookItem.setId(++id);
        }
        return map.put(bookItem.getId(), bookItem);
    }

    @Override
    public void deleteById(Long id) {
        map.remove(id);
    }

    @Override
    public void updateStatus(Long id, BookItemStatus status) {
        BookItem bookItem = map.get(id);
        bookItem.setStatus(status);
        map.put(id, bookItem);
    }
}
