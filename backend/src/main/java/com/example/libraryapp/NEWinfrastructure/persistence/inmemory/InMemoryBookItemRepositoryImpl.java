package com.example.libraryapp.NEWinfrastructure.persistence.inmemory;

import com.example.libraryapp.NEWdomain.bookitem.model.BookItem;
import com.example.libraryapp.NEWdomain.bookitem.model.BookItemStatus;
import com.example.libraryapp.NEWdomain.bookitem.ports.BookItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

public class InMemoryBookItemRepositoryImpl implements BookItemRepository {
    private final ConcurrentHashMap<Long, BookItem> map = new ConcurrentHashMap<>();
    private static long id = 0;

    @Override
    public Optional<BookItem> findById(Long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Page<BookItem> getPageOfBookItems(Pageable pageable) {
        return new PageImpl<>(new ArrayList<>(map.values()), pageable, map.size());
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
