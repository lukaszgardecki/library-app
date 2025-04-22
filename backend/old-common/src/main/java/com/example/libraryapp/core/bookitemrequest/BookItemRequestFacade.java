package com.example.libraryapp.core.bookitemrequest;

import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.bookitemrequest.dto.BookItemRequestDto;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.domain.bookitemrequest.model.RequestId;
import com.example.libraryapp.domain.user.model.UserId;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class BookItemRequestFacade {
    private final GetCurrentBookItemRequestUseCase getCurrentBookItemRequestUseCase;
    private final GetUserCurrentBookItemRequestsUseCase getUserCurrentBookItemRequestsUseCase;
    private final GetPageOfBookItemRequestsByStatusUseCase getPageOfBookItemRequestsByStatusUseCase;
    private final CreateBookItemRequestUseCase createBookItemRequestUseCase;
    private final CancelBookItemRequestUseCase cancelBookItemRequestUseCase;
    private final ChangeBookItemRequestStatusUseCase changeBookItemRequestStatusUseCase;
    private final ChangeBookItemRequestStatusToReadyUseCase changeBookItemRequestStatusToReadyUseCase;
    private final CheckIfBookItemRequestStatusIsReadyUseCase checkIfBookItemRequestStatusIsReadyUseCase;
    private final EnsureBookItemNotRequestedUseCase ensureBookItemNotRequestedUseCase;
    private final IsBookItemRequestedUseCase isBookItemRequestedUseCase;

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

    public void cancelBookItemRequest(BookItemId bookItemId, UserId userId) {
        cancelBookItemRequestUseCase.execute(bookItemId, userId);
    }

    public void cancelAllItemRequestsByUserId(UserId userId) {
        getUserCurrentBookItemRequests(userId)
                .forEach(req -> cancelBookItemRequest(new BookItemId(req.getBookItemId()), userId));
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

    public void ensureBookItemNotRequestedUseCase(BookItemId bookItemId) {
        ensureBookItemNotRequestedUseCase.execute(bookItemId);
    }

    public boolean isBookItemRequested(BookItemId bookItemId) {
        return isBookItemRequestedUseCase.execute(bookItemId);
    }
}
