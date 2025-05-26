package com.example.requestservice.domain.ports.out;

import com.example.requestservice.domain.model.BookItemRequest;
import com.example.requestservice.domain.model.values.BookItemId;
import com.example.requestservice.domain.model.values.BookItemRequestStatus;
import com.example.requestservice.domain.model.values.RequestId;
import com.example.requestservice.domain.model.values.UserId;
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
