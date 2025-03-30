package com.example.libraryapp.domain.warehouse.model;

import com.example.libraryapp.domain.book.dto.BookDto;
import com.example.libraryapp.domain.bookitem.dto.BookItemDto;
import com.example.libraryapp.domain.bookitemrequest.dto.BookItemRequestDto;
import com.example.libraryapp.domain.person.dto.PersonDto;
import com.example.libraryapp.domain.rack.dto.RackDto;
import com.example.libraryapp.domain.user.dto.UserDto;
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
}
