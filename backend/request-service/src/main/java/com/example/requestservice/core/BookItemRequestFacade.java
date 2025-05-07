package com.example.requestservice.core;

import com.example.requestservice.domain.dto.BookItemRequestDto;
import com.example.requestservice.domain.model.*;
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

    public BookItemRequestDto getBookItemRequestById(RequestId requestId) {
        BookItemRequest request = getBookItemRequestUseCase.execute(requestId);
        return BookItemRequestMapper.toDto(request);
    }

    public BookItemRequestDto getCurrentBookItemRequest(BookItemId bookItemId, UserId userId) {
        BookItemRequest bookItemRequest = getCurrentBookItemRequestUseCase.execute(bookItemId, userId);
        return BookItemRequestMapper.toDto(bookItemRequest);
    }

    public List<BookItemRequestDto> getUserCurrentBookItemRequests(UserId userId) {
        return getUserCurrentBookItemRequestsUseCase.execute(userId)
                .stream()
                .map(BookItemRequestMapper::toDto)
                .toList();
    }

    public Page<BookItemRequestDto> getPageOfBookRequestsByStatus(@Nullable BookItemRequestStatus status, Pageable pageable) {
        return getPageOfBookItemRequestsByStatusUseCase.execute(status, pageable)
                .map(BookItemRequestMapper::toDto);
    }

    public BookItemRequestDto requestBookItem(BookItemId bookItemId, UserId userId) {
        BookItemRequest bookItemRequest = createBookItemRequestUseCase.execute(bookItemId, userId);
        return BookItemRequestMapper.toDto(bookItemRequest);
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
