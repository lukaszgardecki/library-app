package com.example.requestservice.domain.ports;

import com.example.requestservice.domain.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookItemRequestRepositoryPort {

    Optional<BookItemRequest> findById(RequestId id);

    List<BookItemRequest> findAll(BookItemId bookItemId, UserId userId);

    List<BookItemRequest> findAllByUserIdAndStatuses(UserId userId, List<BookItemRequestStatus> statusesToFind);

    List<BookItemRequest> findByBookItemIdAndStatuses(BookItemId bookItemId, List<BookItemRequestStatus> statusesToFind);

    Page<BookItemRequest> findAllByStatus(BookItemRequestStatus status, Pageable pageable);

    BookItemRequest save(BookItemRequest request);

    void setBookRequestStatus(RequestId id, BookItemRequestStatus status);
}
