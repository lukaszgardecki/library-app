package com.example.requestservice.infrastructure.websockets;

import com.example.requestservice.core.BookItemRequestFacade;
import com.example.requestservice.domain.model.values.BookItemRequestStatus;
import com.example.requestservice.domain.model.values.RequestId;
import com.example.requestservice.infrastructure.http.dto.WarehouseBookItemRequestListViewDto;
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
    WarehouseBookItemRequestListViewDto addToInProgress(@Payload WarehouseBookItemRequestListViewDto bookItemRequest) {
        BookItemRequestStatus status = BookItemRequestStatus.IN_PROGRESS;
        bookItemRequestFacade.changeBookItemRequestStatus(new RequestId(bookItemRequest.getId()), status);
        return bookItemRequest;
    }

    @MessageMapping("/warehouse/move_to_pending")
    @SendTo("/queue/warehouse/remove_from_in-progress")
    WarehouseBookItemRequestListViewDto addToPending(@Payload WarehouseBookItemRequestListViewDto bookItemRequest) {
        BookItemRequestStatus status = BookItemRequestStatus.PENDING;
        bookItemRequestFacade.changeBookItemRequestStatus(new RequestId(bookItemRequest.getId()), status);
        return bookItemRequest;
    }
}
