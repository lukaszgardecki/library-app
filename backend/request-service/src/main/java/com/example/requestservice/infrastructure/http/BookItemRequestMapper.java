package com.example.requestservice.infrastructure.http;

import com.example.requestservice.domain.integration.catalog.BookId;
import com.example.requestservice.domain.integration.catalog.dto.BookDto;
import com.example.requestservice.domain.integration.catalog.dto.BookItemDto;
import com.example.requestservice.domain.integration.user.dto.PersonDto;
import com.example.requestservice.domain.integration.user.dto.UserDto;
import com.example.requestservice.domain.integration.warehouse.RackId;
import com.example.requestservice.domain.integration.warehouse.ShelfId;
import com.example.requestservice.domain.integration.warehouse.dto.RackDto;
import com.example.requestservice.domain.integration.warehouse.dto.ShelfDto;
import com.example.requestservice.domain.model.BookItemRequest;
import com.example.requestservice.domain.model.values.*;
import com.example.requestservice.domain.ports.out.CatalogServicePort;
import com.example.requestservice.domain.ports.out.UserServicePort;
import com.example.requestservice.domain.ports.out.WarehouseServicePort;
import com.example.requestservice.infrastructure.http.dto.BookItemRequestDto;
import com.example.requestservice.infrastructure.http.dto.WarehouseBookItemRequestListViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookItemRequestMapper {
    private final CatalogServicePort catalogService;
    private final UserServicePort userService;
    private final WarehouseServicePort warehouseService;

    public BookItemRequestDto toDto(BookItemRequest model) {
        return new BookItemRequestDto(
                model.getId().value(),
                model.getCreationDate().value(),
                model.getStatus().name(),
                model.getUserId().value(),
                model.getBookItemId().value()
        );
    }

    public BookItemRequest toModel(BookItemRequestDto model) {
        return new BookItemRequest(
                new RequestId(model.getId()),
                new BookItemRequestCreationDate(model.getCreationDate()),
                BookItemRequestStatus.valueOf(model.getStatus()),
                new UserId(model.getUserId()),
                new BookItemId(model.getBookItemId())
        );
    }

    public WarehouseBookItemRequestListViewDto toWarehouseDto(BookItemRequest request) {
        BookItemDto bookItem = catalogService.getBookItemById(request.getBookItemId());
        BookDto book = catalogService.getBookById(new BookId(bookItem.getBookId()));
        UserDto user = userService.getUserById(request.getUserId());
        // TODO: 13.05.2025 sprwadzić to person jeśli nie potrzebne to usunąć
        PersonDto person = userService.getPersonByUserId(new UserId(user.getId()));
        RackDto rack = warehouseService.getRackById(new RackId(bookItem.getRackId()));
        ShelfDto shelf = warehouseService.getShelfById(new ShelfId(bookItem.getShelfId()));
        return new WarehouseBookItemRequestListViewDto(
                request.getId().value(),
                request.getStatus(),
                request.getCreationDate().value(),
                book.getTitle(),
                bookItem.getBarcode(),
                book.getFormat(),
                rack.getName(),
                shelf.getName()
        );
    }
}
