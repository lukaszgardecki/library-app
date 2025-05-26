package com.example.requestservice.core;

import com.example.requestservice.domain.model.BookItemRequest;
import com.example.requestservice.domain.model.values.BookItemId;
import com.example.requestservice.domain.model.values.BookItemRequestStatus;
import com.example.requestservice.domain.model.values.RequestId;
import com.example.requestservice.domain.model.values.UserId;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class BookItemRequestFacade {
    private final GetBookItemRequestUseCase getBookItemRequestUseCase;
    private final GetCurrentBookItemRequestUseCase getCurrentBookItemRequestUseCase;
    private final GetUserCurrentBookItemRequestsUseCase getUserCurrentBookItemRequestsUseCase;
    private final GetPageOfBookItemRequestsByStatusUseCase getPageOfBookItemRequestsByStatusUseCase;
    private final CreateBookItemRequestUseCase createBookItemRequestUseCase;
    private final CancelAllBookItemRequestsUseCase cancelAllBookItemRequestsUseCase;
    private final ChangeBookItemRequestStatusUseCase changeBookItemRequestStatusUseCase;
    private final ChangeBookItemRequestStatusToReadyUseCase changeBookItemRequestStatusToReadyUseCase;
    private final CheckIfBookItemRequestStatusIsReadyUseCase checkIfBookItemRequestStatusIsReadyUseCase;
    private final EnsureBookItemNotRequestedUseCase ensureBookItemNotRequestedUseCase;
    private final IsBookItemRequestedUseCase isBookItemRequestedUseCase;

    public BookItemRequest getBookItemRequestById(RequestId requestId) {
        return getBookItemRequestUseCase.execute(requestId);
    }

    public BookItemRequest getCurrentBookItemRequest(BookItemId bookItemId, UserId userId) {
        return getCurrentBookItemRequestUseCase.execute(bookItemId, userId);
    }

    public List<BookItemRequest> getUserCurrentBookItemRequests(UserId userId) {
        return getUserCurrentBookItemRequestsUseCase.execute(userId)
                .stream()
                .toList();
    }

    public Page<BookItemRequest> getPageOfBookRequestsByStatus(@Nullable BookItemRequestStatus status, Pageable pageable) {
        return getPageOfBookItemRequestsByStatusUseCase.execute(status, pageable);
    }

    public BookItemRequest requestBookItem(BookItemId bookItemId, UserId userId) {
        return createBookItemRequestUseCase.execute(bookItemId, userId);
    }

    public void cancelAllBookItemRequestsByUserId(UserId userId) {
        cancelAllBookItemRequestsUseCase.execute(userId);
    }

    public void changeBookItemRequestStatus(RequestId bookRequestId, BookItemRequestStatus newStatus) {
        changeBookItemRequestStatusUseCase.execute(bookRequestId, newStatus);
    }

    public void changeBookRequestStatusToReady(RequestId bookRequestId) {
        changeBookItemRequestStatusToReadyUseCase.execute(bookRequestId);
    }

    public RequestId checkIfBookItemRequestStatusIsReady(BookItemId bookItemId, UserId userId) {
        return checkIfBookItemRequestStatusIsReadyUseCase.execute(bookItemId, userId);
    }

    public void ensureBookItemNotRequested(BookItemId bookItemId) {
        ensureBookItemNotRequestedUseCase.execute(bookItemId);
    }

    public boolean isBookItemRequested(BookItemId bookItemId) {
        return isBookItemRequestedUseCase.execute(bookItemId);
    }
}
