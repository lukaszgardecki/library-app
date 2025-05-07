package com.example.warehouseservice.domain.model;

import com.example.warehouseservice.domain.dto.*;
import com.example.warehouseservice.domain.integration.catalog.dto.BookDto;
import com.example.warehouseservice.domain.integration.catalog.dto.BookItemDto;
import com.example.warehouseservice.domain.integration.request.dto.BookItemRequestDto;
import com.example.warehouseservice.domain.integration.user.dto.PersonDto;
import com.example.warehouseservice.domain.integration.user.dto.UserDto;
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
