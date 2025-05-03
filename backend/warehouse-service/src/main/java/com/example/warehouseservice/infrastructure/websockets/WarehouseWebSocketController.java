package com.example.warehouseservice.infrastructure.websockets;

import com.example.warehouseservice.domain.dto.WarehouseBookItemRequestListViewDto;
import com.example.warehouseservice.domain.model.BookItemRequestStatus;
import com.example.warehouseservice.domain.model.RequestId;
import com.example.warehouseservice.domain.ports.BookItemRequestServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
class WarehouseWebSocketController {
    private final BookItemRequestServicePort bookItemRequestService;

    @MessageMapping("/warehouse/move_to_in_progress")
    @SendTo("/queue/warehouse/remove_from_pending")
    WarehouseBookItemRequestListViewDto addToInProgress(@Payload WarehouseBookItemRequestListViewDto bookItemRequest) {
        BookItemRequestStatus status = BookItemRequestStatus.IN_PROGRESS;
        bookItemRequestService.changeBookItemRequestStatus(new RequestId(bookItemRequest.getId()), status);
        return bookItemRequest;
    }

    @MessageMapping("/warehouse/move_to_pending")
    @SendTo("/queue/warehouse/remove_from_in-progress")
    WarehouseBookItemRequestListViewDto addToPending(@Payload WarehouseBookItemRequestListViewDto bookItemRequest) {
        BookItemRequestStatus status = BookItemRequestStatus.PENDING;
        bookItemRequestService.changeBookItemRequestStatus(new RequestId(bookItemRequest.getId()), status);
        return bookItemRequest;
    }
}
