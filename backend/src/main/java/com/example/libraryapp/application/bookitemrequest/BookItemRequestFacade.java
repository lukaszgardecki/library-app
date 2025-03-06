package com.example.libraryapp.application.bookitemrequest;

import com.example.libraryapp.domain.bookitemrequest.dto.BookItemRequestDto;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
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

    public BookItemRequestDto getCurrentBookItemRequest(Long bookItemId, Long userId) {
        BookItemRequest bookItemRequest = getCurrentBookItemRequestUseCase.execute(bookItemId, userId);
        return BookItemRequestMapper.toDto(bookItemRequest);
    }

    public List<BookItemRequestDto> getUserCurrentBookItemRequests(Long userId) {
        return getUserCurrentBookItemRequestsUseCase.execute(userId)
                .stream()
                .map(BookItemRequestMapper::toDto)
                .toList();
    }

    public Page<BookItemRequestDto> getPageOfBookRequestsByStatus(BookItemRequestStatus status, Pageable pageable) {
        return getPageOfBookItemRequestsByStatusUseCase.execute(status, pageable)
                .map(BookItemRequestMapper::toDto);
    }

    public BookItemRequestDto requestBookItem(Long bookItemId, Long userId) {
        BookItemRequest bookItemRequest = createBookItemRequestUseCase.execute(bookItemId, userId);
        return BookItemRequestMapper.toDto(bookItemRequest);
    }

    public void cancelBookItemRequest(Long bookItemId, Long userId) {
        cancelBookItemRequestUseCase.execute(bookItemId, userId);
    }

    public void cancelAllItemRequestsByUserId(Long userId) {
        getUserCurrentBookItemRequests(userId)
                .forEach(req -> cancelBookItemRequest(req.getBookItemId(), userId));
    }

    public void changeBookItemRequestStatus(Long bookRequestId, BookItemRequestStatus newStatus) {
        changeBookItemRequestStatusUseCase.execute(bookRequestId, newStatus);
    }

    public void changeBookRequestStatusToReady(Long bookRequestId) {
        changeBookItemRequestStatusToReadyUseCase.execute(bookRequestId);
    }

    public Long checkIfBookItemRequestStatusIsReady(Long bookItemId, Long userId) {
        return checkIfBookItemRequestStatusIsReadyUseCase.execute(bookItemId, userId);
    }

    public void ensureBookItemNotRequestedUseCase(Long bookItemId) {
        ensureBookItemNotRequestedUseCase.execute(bookItemId);
    }

    public boolean isBookItemRequested(Long bookItemId) {
        return isBookItemRequestedUseCase.execute(bookItemId);
    }
}
