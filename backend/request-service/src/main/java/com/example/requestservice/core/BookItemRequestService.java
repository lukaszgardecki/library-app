package com.example.requestservice.core;

import com.example.requestservice.domain.i18n.MessageKey;
import com.example.requestservice.domain.exceptions.BookItemRequestException;
import com.example.requestservice.domain.exceptions.BookItemRequestNotFoundException;
import com.example.requestservice.domain.model.*;
import com.example.requestservice.domain.model.values.*;
import com.example.requestservice.domain.ports.out.BookItemRequestRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
class BookItemRequestService {
    private final BookItemRequestRepositoryPort bookItemRequestRepository;

    BookItemRequest getBookItemRequestById(RequestId id) {
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
        BookItemRequest bookItemRequest = getBookItemRequestById(bookItemRequestId);
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
