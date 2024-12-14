package com.example.libraryapp.NEWapplication.bookitemrequest;

import com.example.libraryapp.NEWdomain.bookitemrequest.dto.BookItemRequestDto;
import com.example.libraryapp.NEWdomain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.NEWdomain.bookitemrequest.model.BookItemRequestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class BookItemRequestFacade {
    private final GetBookItemRequestByParamsUseCase getBookItemRequestByParamsUseCase;
    private final GetPageOfBookItemRequestsByStatusUseCase getPageOfBookItemRequestsByStatusUseCase;
    private final CreateBookItemRequestUseCase createBookItemRequestUseCase;
    private final ChangeBookItemRequestStatusUseCase changeBookItemRequestStatusUseCase;
    private final ChangeBookItemRequestStatusToReadyUseCase changeBookItemRequestStatusToReadyUseCase;
    private final CheckBookItemRequestStatusUseCase checkBookItemRequestStatusUseCase;
    private final EnsureBookItemNotRequestedUseCase ensureBookItemNotRequestedUseCase;
    private final IsBookItemRequestedUseCase isBookItemRequestedUseCase;

    public BookItemRequestDto getBookItemRequest(Long bookItemId, Long userId) {
        BookItemRequest bookItemRequest = getBookItemRequestByParamsUseCase.execute(bookItemId, userId);
        return BookItemRequestMapper.toDto(bookItemRequest);
    }

    public Page<BookItemRequestDto> getPageOfBookRequestsByStatus(BookItemRequestStatus status, Pageable pageable) {
        return getPageOfBookItemRequestsByStatusUseCase.execute(status, pageable)
                .map(BookItemRequestMapper::toDto);
    }

    public BookItemRequestDto requestBookItem(Long bookItemId, Long userId) {
        BookItemRequest bookItemRequest = createBookItemRequestUseCase.execute(bookItemId, userId);
        return BookItemRequestMapper.toDto(bookItemRequest);
    }

    public void changeBookItemRequestStatus(Long bookRequestId, BookItemRequestStatus newStatus) {
        changeBookItemRequestStatusUseCase.execute(bookRequestId, newStatus);
    }

    public void changeBookRequestStatusToReady(Long bookRequestId) {
        changeBookItemRequestStatusToReadyUseCase.execute(bookRequestId);
    }

    public Long checkBookItemRequestStatus(Long bookItemId, Long userId, BookItemRequestStatus requiredStatus) {
        return checkBookItemRequestStatusUseCase.execute(bookItemId, userId, requiredStatus);
    }

    public void ensureBookItemNotRequestedUseCase(Long bookItemId) {
        ensureBookItemNotRequestedUseCase.execute(bookItemId);
    }

    public boolean isBookItemRequested(Long bookItemId) {
        return isBookItemRequestedUseCase.execute(bookItemId);
    }
}
