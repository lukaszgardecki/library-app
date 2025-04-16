package com.example.libraryapp.domain.bookitemrequest.ports;

import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.domain.bookitemrequest.model.RequestId;
import com.example.libraryapp.domain.user.model.UserId;
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
