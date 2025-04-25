package com.example.requestservice.core;

import com.example.requestservice.domain.model.BookItemRequest;
import com.example.requestservice.domain.model.UserId;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class GetUserCurrentBookItemRequestsUseCase {
//    private final AuthenticationFacade authFacade;
    private final BookItemRequestService bookItemRequestService;

    List<BookItemRequest> execute(UserId userId) {
//        authFacade.validateOwnerOrAdminAccess(userId);
        return bookItemRequestService.getAllCurrentBookItemRequestsByUserId(userId);
    }
}
