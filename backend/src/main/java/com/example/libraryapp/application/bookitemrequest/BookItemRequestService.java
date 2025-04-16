package com.example.libraryapp.application.bookitemrequest;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.bookitemrequest.exceptions.BookItemRequestException;
import com.example.libraryapp.domain.bookitemrequest.exceptions.BookItemRequestNotFoundException;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestCreationDate;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.domain.bookitemrequest.model.RequestId;
import com.example.libraryapp.domain.bookitemrequest.ports.BookItemRequestRepositoryPort;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
class BookItemRequestService {
    private final BookItemRequestRepositoryPort bookItemRequestRepository;

    BookItemRequest getCurrentBookItemRequestById(RequestId id) {
        return bookItemRequestRepository.findById(id)
                .orElseThrow(() -> new BookItemRequestNotFoundException(id));
    }

    BookItemRequest getCurrentBookItemRequest(BookItemId bookItemId, UserId userId) {
        return findCurrentBookItemRequest(bookItemId, userId)
                .orElseThrow(BookItemRequestNotFoundException::new);
    }

    List<BookItemRequest> getAllCurrentBookItemRequestsByUserId(UserId userId) {
        List<BookItemRequestStatus> statusesToFind = getCurrentRequestStatuses();
        return bookItemRequestRepository.findAllByUserIdAndStatuses(userId, statusesToFind);
    }

    List<BookItemRequest> getAllByBookItemIdAndStatuses(BookItemId bookItemId, List<BookItemRequestStatus> statuses) {
        return bookItemRequestRepository.findByBookItemIdAndStatuses(bookItemId, statuses);
    }

    BookItemRequest saveRequest(BookItemId bookItemId, UserId userId) {
        BookItemRequestStatus status = BookItemRequestStatus.PENDING;
        BookItemRequest request = createBookItemRequestToSave(bookItemId, userId, status);
        return bookItemRequestRepository.save(request);
    }

    BookItemRequest saveReservation(BookItemId bookItemId, UserId userId) {
        BookItemRequestStatus status = BookItemRequestStatus.RESERVED;
        BookItemRequest request = createBookItemRequestToSave(bookItemId, userId, status);
        return bookItemRequestRepository.save(request);
    }

    void cancelRequest(RequestId bookItemRequestId) {
        BookItemRequest bookItemRequest = getCurrentBookItemRequestById(bookItemRequestId);
        bookItemRequest.setStatus(BookItemRequestStatus.CANCELED);
        bookItemRequestRepository.save(bookItemRequest);
    }

    int getReservationQueuePosition(BookItemId bookItemId, UserId userId) {
        return bookItemRequestRepository.findByBookItemIdAndStatuses(
                        bookItemId, List.of(BookItemRequestStatus.RESERVED)
                ).stream()
                .sorted(Comparator.comparing(request -> request.getCreationDate().value()))
                .map(BookItemRequest::getUserId)
                .toList()
                .indexOf(userId) + 1;
    }

    void verifyIfCurrentRequestExists(BookItemId bookItemId, UserId userId) {
        findCurrentBookItemRequest(bookItemId, userId)
                .ifPresent(r -> { throw new BookItemRequestException(MessageKey.REQUEST_ALREADY_CREATED); });
    }

    List<BookItemRequestStatus> getCurrentRequestStatuses() {
        return List.of(
                BookItemRequestStatus.PENDING,
                BookItemRequestStatus.IN_PROGRESS,
                BookItemRequestStatus.READY,
                BookItemRequestStatus.RESERVED
        );
    }

    private Optional<BookItemRequest> findCurrentBookItemRequest(BookItemId bookItemId, UserId userId) {
        List<BookItemRequestStatus> statusesToFind = getCurrentRequestStatuses();
        return bookItemRequestRepository.findAll(bookItemId, userId)
                .stream()
                .filter(req -> statusesToFind.contains(req.getStatus()))
                .findAny();
    }

    private BookItemRequest createBookItemRequestToSave(BookItemId bookItemId, UserId userId, BookItemRequestStatus status) {
        return BookItemRequest.builder()
                .creationDate(new BookItemRequestCreationDate(LocalDateTime.now()))
                .status(status)
                .userId(userId)
                .bookItemId(bookItemId)
                .build();
    }
}
