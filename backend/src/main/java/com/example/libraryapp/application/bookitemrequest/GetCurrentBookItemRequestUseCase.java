package com.example.libraryapp.application.bookitemrequest;

import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetCurrentBookItemRequestUseCase {
    private final BookItemRequestService bookItemRequestService;

    BookItemRequest execute(BookItemId bookItemId, UserId userId) {
        return bookItemRequestService.getCurrentBookItemRequest(bookItemId, userId);
    }
}
