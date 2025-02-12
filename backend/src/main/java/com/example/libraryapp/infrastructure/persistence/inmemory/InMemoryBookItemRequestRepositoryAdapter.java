package com.example.libraryapp.infrastructure.persistence.inmemory;

import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.domain.bookitemrequest.ports.BookItemRequestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryBookItemRequestRepositoryAdapter implements BookItemRequestRepository {
    private final ConcurrentHashMap<Long, BookItemRequest> map = new ConcurrentHashMap<>();
    private static long id = 0;

    @Override
    public Optional<BookItemRequest> findById(Long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public List<BookItemRequest> findAll(Long bookItemId, Long userId) {
        return map.values().stream()
                .filter(request -> request.getBookItemId().equals(bookItemId) && request.getUserId().equals(userId))
                .toList();
    }

    @Override
    public List<BookItemRequest> findAllByUserIdAndStatuses(Long userId, List<BookItemRequestStatus> statusesToFind) {
        return map.values().stream()
                .filter(request -> request.getUserId().equals(userId) && statusesToFind.contains(request.getStatus()))
                .toList();
    }

    @Override
    public List<BookItemRequest> findByBookItemIdAndStatuses(Long bookItemId, List<BookItemRequestStatus> statusesToFind) {
        return map.values().stream()
                .filter(request -> request.getBookItemId().equals(bookItemId) && statusesToFind.contains(request.getStatus()))
                .toList();
    }


    @Override
    public Page<BookItemRequest> findAllByStatus(BookItemRequestStatus status, Pageable pageable) {
        List<BookItemRequest> filteredRequests = map.values().stream()
                .filter(request -> request.getStatus().equals(status))
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filteredRequests.size());

        List<BookItemRequest> pagedRequests = filteredRequests.subList(start, end);

        return new PageImpl<>(pagedRequests, pageable, filteredRequests.size());
    }


    @Override
    public BookItemRequest save(BookItemRequest request) {
        if (request.getId() == null) {
            request.setId(++id);
        }
        return map.put(request.getId(), request);
    }


    @Override
    public void setBookRequestStatus(Long id, BookItemRequestStatus status) {
        BookItemRequest request = map.get(id);
        if (request != null) {
            request.setStatus(status);
        } else {
            throw new IllegalArgumentException("BookItemRequest not found with id: " + id);
        }
    }
}
