package com.example.libraryapp.NEWdomain.bookitemrequest.ports;

import com.example.libraryapp.NEWdomain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.NEWdomain.bookitemrequest.model.BookItemRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookItemRequestRepository {

    Optional<BookItemRequest> findById(Long id);

    Optional<BookItemRequest> find(Long bookItemId, Long userId);

    Page<BookItemRequest> findAllByStatus(BookItemRequestStatus status, Pageable pageable);

    List<BookItemRequest> findByBookItemIdAndStatuses(Long bookItemId, List<BookItemRequestStatus> statusesToFind);

    BookItemRequest save(BookItemRequest request);

    void setBookRequestStatus(Long id, BookItemRequestStatus status);
}
