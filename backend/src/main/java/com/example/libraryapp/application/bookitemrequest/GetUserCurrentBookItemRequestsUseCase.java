package com.example.libraryapp.application.bookitemrequest;

import com.example.libraryapp.application.auth.AuthenticationFacade;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class GetUserCurrentBookItemRequestsUseCase {
    private final AuthenticationFacade authFacade;
    private final BookItemRequestService bookItemRequestService;

    List<BookItemRequest> execute(UserId userId) {
        authFacade.validateOwnerOrAdminAccess(userId);
        return bookItemRequestService.getAllCurrentBookItemRequestsByUserId(userId);
    }
}
