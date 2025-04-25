package com.example.warehouseservice.core.bookitemrequest;

import com.example.warehouseservice.core.rack.RackFacade;
import com.example.warehouseservice.core.shelf.ShelfFacade;
import com.example.warehouseservice.domain.dto.*;
import com.example.warehouseservice.domain.model.*;
import com.example.warehouseservice.domain.model.rack.RackId;
import com.example.warehouseservice.domain.model.shelf.ShelfId;
import com.example.warehouseservice.domain.ports.BookItemRequestServicePort;
import com.example.warehouseservice.domain.ports.CatalogServicePort;
import com.example.warehouseservice.domain.ports.EventListenerPort;
import com.example.warehouseservice.domain.ports.UserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
class BookItemRequestService implements EventListenerPort {
    private final CatalogServicePort catalogService;
    private final UserServicePort userService;
    private final BookItemRequestServicePort bookItemRequestService;
    private final RackFacade rackFacade;
    private final ShelfFacade shelfFacade;

    Page<WarehouseBookItemRequest> getBookRequestList(BookItemRequestStatus status, Pageable pageable) {
        Page<BookItemRequestDto> requests = bookItemRequestService.getPageOfBookRequestsByStatus(status, pageable);
        return requests.map(this::getWarehouseBookItemRequest);
    }

    WarehouseBookItemRequest getWarehouseBookItemRequest(RequestId requestId) {
        BookItemRequestDto request = bookItemRequestService.getBookItemRequestById(requestId);
        return getWarehouseBookItemRequest(request);
    }

    private WarehouseBookItemRequest getWarehouseBookItemRequest(BookItemRequestDto request) {
        BookItemDto bookItem = catalogService.getBookItemById(new BookItemId(request.getBookItemId()));
        BookDto book = catalogService.getBookById(new BookId(bookItem.getBookId()));
        UserDto user = userService.getUserById(new UserId(request.getUserId()));
        PersonDto person = userService.getPersonByUserId(new UserId(user.getId()));
        RackDto rack = rackFacade.getRackById(new RackId(bookItem.getRackId()));
        ShelfDto shelf = shelfFacade.getShelfById(new ShelfId(bookItem.getShelfId()));
        return new WarehouseBookItemRequest(book, bookItem, request, user, person, rack, shelf);
    }
}
