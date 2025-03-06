package com.example.libraryapp.application.bookitemrequest;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.bookitemrequest.exceptions.BookItemRequestException;
import com.example.libraryapp.domain.bookitemrequest.exceptions.BookItemRequestNotFoundException;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.domain.bookitemrequest.ports.BookItemRequestRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
class BookItemRequestService {
    private final BookItemRequestRepositoryPort bookItemRequestRepository;

    BookItemRequest getCurrentBookItemRequestById(Long id) {
        return bookItemRequestRepository.findById(id)
                .orElseThrow(() -> new BookItemRequestNotFoundException(id));
    }

    BookItemRequest getCurrentBookItemRequest(Long bookItemId, Long userId) {
        return findCurrentBookItemRequest(bookItemId, userId)
                .orElseThrow(BookItemRequestNotFoundException::new);
    }

    List<BookItemRequest> getAllCurrentBookItemRequestsByUserId(Long userId) {
        List<BookItemRequestStatus> statusesToFind = getCurrentRequestStatuses();
        return bookItemRequestRepository.findAllByUserIdAndStatuses(userId, statusesToFind);
    }

    List<BookItemRequest> getAllByBookItemIdAndStatuses(Long bookItemId, List<BookItemRequestStatus> statuses) {
        return bookItemRequestRepository.findByBookItemIdAndStatuses(bookItemId, statuses);
    }

    BookItemRequest saveRequest(Long bookItemId, Long userId) {
        BookItemRequestStatus status = BookItemRequestStatus.PENDING;
        BookItemRequest request = createBookItemRequestToSave(bookItemId, userId, status);
        return bookItemRequestRepository.save(request);
    }

    BookItemRequest saveReservation(Long bookItemId, Long userId) {
        BookItemRequestStatus status = BookItemRequestStatus.RESERVED;
        BookItemRequest request = createBookItemRequestToSave(bookItemId, userId, status);
        return bookItemRequestRepository.save(request);
    }

    void cancelRequest(Long bookItemRequestId) {
        BookItemRequest bookItemRequest = getCurrentBookItemRequestById(bookItemRequestId);
        bookItemRequest.setStatus(BookItemRequestStatus.CANCELED);
        bookItemRequestRepository.save(bookItemRequest);
    }

    int getReservationQueuePosition(Long bookItemId, Long userId) {
        return bookItemRequestRepository.findByBookItemIdAndStatuses(
                        bookItemId, List.of(BookItemRequestStatus.RESERVED)
                ).stream()
                .sorted(Comparator.comparing(BookItemRequest::getCreationDate))
                .map(BookItemRequest::getUserId)
                .toList()
                .indexOf(userId) + 1;
    }

    void verifyIfCurrentRequestExists(Long bookItemId, Long userId) {
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

    private Optional<BookItemRequest> findCurrentBookItemRequest(Long bookItemId, Long userId) {
        List<BookItemRequestStatus> statusesToFind = getCurrentRequestStatuses();
        return bookItemRequestRepository.findAll(bookItemId, userId)
                .stream()
                .filter(req -> statusesToFind.contains(req.getStatus()))
                .findAny();
    }

    private BookItemRequest createBookItemRequestToSave(Long bookItemId, Long userId, BookItemRequestStatus status) {
        return BookItemRequest.builder()
                .creationDate(LocalDateTime.now())
                .status(status)
                .userId(userId)
                .bookItemId(bookItemId)
                .build();
    }
}
