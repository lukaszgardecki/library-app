package com.example.libraryapp.NEWapplication.bookitemrequest;

import com.example.libraryapp.NEWdomain.bookitemrequest.model.BookItemRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetBookItemRequestByParamsUseCase {
    private final BookItemRequestService bookItemRequestService;

    BookItemRequest execute(Long bookItemId, Long userId) {
        return bookItemRequestService.findBookItemRequest(bookItemId, userId);
    }
}
