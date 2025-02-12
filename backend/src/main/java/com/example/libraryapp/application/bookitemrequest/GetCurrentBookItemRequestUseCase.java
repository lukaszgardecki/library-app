package com.example.libraryapp.application.bookitemrequest;

import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetCurrentBookItemRequestUseCase {
    private final BookItemRequestService bookItemRequestService;

    BookItemRequest execute(Long bookItemId, Long userId) {
        return bookItemRequestService.getCurrentBookItemRequest(bookItemId, userId);
    }
}
