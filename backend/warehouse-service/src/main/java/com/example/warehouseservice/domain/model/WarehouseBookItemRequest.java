package com.example.warehouseservice.domain.model;

import com.example.warehouseservice.domain.dto.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WarehouseBookItemRequest {
    private final BookDto book;
    private final BookItemDto bookItem;
    private final BookItemRequestDto bookItemRequest;
    private final UserDto user;
    private final PersonDto person;
    private final RackDto rack;
    private final ShelfDto shelf;
}
