package com.example.libraryapp.adapter.websockets;

import com.example.libraryapp.application.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.domain.bookitemrequest.dto.BookItemRequestDto;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.domain.bookitemrequest.model.RequestId;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
class WarehouseWebSocketController {
    private final BookItemRequestFacade bookItemRequestFacade;

    @MessageMapping("/warehouse/move_to_in_progress")
    @SendTo("/queue/warehouse/remove_from_pending")
    public BookItemRequestDto addToInProgress(@Payload BookItemRequestDto bookItemRequest) {
        BookItemRequestStatus status = BookItemRequestStatus.IN_PROGRESS;
        bookItemRequest.setStatus(status);
        bookItemRequestFacade.changeBookItemRequestStatus(new RequestId(bookItemRequest.getId()), status);
        return bookItemRequest;
    }

    @MessageMapping("/warehouse/move_to_pending")
    @SendTo("/queue/warehouse/remove_from_in-progress")
    public BookItemRequestDto addToPending(@Payload BookItemRequestDto bookItemRequest) {
        BookItemRequestStatus status = BookItemRequestStatus.PENDING;
        bookItemRequest.setStatus(status);
        bookItemRequestFacade.changeBookItemRequestStatus(new RequestId(bookItemRequest.getId()), status);
        return bookItemRequest;
    }
}
