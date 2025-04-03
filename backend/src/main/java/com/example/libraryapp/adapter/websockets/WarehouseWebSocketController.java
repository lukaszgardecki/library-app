package com.example.libraryapp.adapter.websockets;

import com.example.libraryapp.application.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.domain.bookitemrequest.model.RequestId;
import com.example.libraryapp.domain.warehouse.dto.WarehouseBookItemRequestListViewDto;
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
    public WarehouseBookItemRequestListViewDto addToInProgress(@Payload WarehouseBookItemRequestListViewDto bookItemRequest) {
        BookItemRequestStatus status = BookItemRequestStatus.IN_PROGRESS;
        bookItemRequestFacade.changeBookItemRequestStatus(new RequestId(bookItemRequest.getId()), status);
        return bookItemRequest;
    }

    @MessageMapping("/warehouse/move_to_pending")
    @SendTo("/queue/warehouse/remove_from_in-progress")
    public WarehouseBookItemRequestListViewDto addToPending(@Payload WarehouseBookItemRequestListViewDto bookItemRequest) {
        BookItemRequestStatus status = BookItemRequestStatus.PENDING;
        bookItemRequestFacade.changeBookItemRequestStatus(new RequestId(bookItemRequest.getId()), status);
        return bookItemRequest;
    }
}
