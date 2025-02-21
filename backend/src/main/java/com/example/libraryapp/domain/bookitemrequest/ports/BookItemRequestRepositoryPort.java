package com.example.libraryapp.domain.bookitemrequest.ports;

import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookItemRequestRepositoryPort {

    Optional<BookItemRequest> findById(Long id);

    List<BookItemRequest> findAll(Long bookItemId, Long userId);

    List<BookItemRequest> findAllByUserIdAndStatuses(Long userId, List<BookItemRequestStatus> statusesToFind);

    List<BookItemRequest> findByBookItemIdAndStatuses(Long bookItemId, List<BookItemRequestStatus> statusesToFind);

    Page<BookItemRequest> findAllByStatus(BookItemRequestStatus status, Pageable pageable);

    BookItemRequest save(BookItemRequest request);

    void setBookRequestStatus(Long id, BookItemRequestStatus status);
}
