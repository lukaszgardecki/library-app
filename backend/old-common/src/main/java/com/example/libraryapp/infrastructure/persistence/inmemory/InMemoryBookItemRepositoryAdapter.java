package com.example.libraryapp.infrastructure.persistence.inmemory;

import com.example.libraryapp.domain.book.model.BookId;
import com.example.libraryapp.domain.bookitem.model.BookItem;
import com.example.libraryapp.domain.bookitem.model.BookItemBarcode;
import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.bookitem.model.BookItemStatus;
import com.example.libraryapp.domain.bookitem.ports.BookItemRepositoryPort;
import com.example.libraryapp.domain.rack.model.RackId;
import com.example.libraryapp.domain.shelf.model.ShelfId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

public class InMemoryBookItemRepositoryAdapter implements BookItemRepositoryPort {
    private final ConcurrentHashMap<BookItemId, BookItem> map = new ConcurrentHashMap<>();
    private static long id = 0;

    @Override
    public Optional<BookItem> findById(BookItemId id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Page<BookItem> findAllByParams(BookId bookId, RackId rackId, ShelfId shelfId, String query, Pageable pageable) {
        List<BookItem> filteredItems = map.values().stream()
                .filter(bookItem -> (bookId == null || bookItem.getBookId().equals(bookId)))
                .filter(bookItem -> (rackId == null || bookItem.getRackId().equals(rackId)))
                .filter(bookItem -> (shelfId == null || bookItem.getShelfId().equals(shelfId)))
                .filter(bookItem -> (query == null || bookItem.getBarcode().value().toLowerCase().contains(query.toLowerCase())))
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
            bookItem.setId(new BookItemId(++id));
        }
        return map.put(bookItem.getId(), bookItem);
    }

    @Override
    public void deleteById(BookItemId id) {
        map.remove(id);
    }

    @Override
    public Long countByParams(RackId rackId, ShelfId shelfId) {
        return map.values().stream()
                .filter(item -> rackId == null || item.getRackId().value().equals(rackId.value()))
                .filter(item -> shelfId == null || item.getShelfId().value().equals(shelfId.value()))
                .count();
    }

    @Override
    public void updateStatus(BookItemId id, BookItemStatus status) {
        BookItem bookItem = map.get(id);
        bookItem.setStatus(status);
        map.put(id, bookItem);
    }

    @Override
    public void updateBarcode(BookItemId id, BookItemBarcode barcode) {
        BookItem bookItem = map.get(id);
        bookItem.setBarcode(barcode);
        map.put(id, bookItem);
    }
}
