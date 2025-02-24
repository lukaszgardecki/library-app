package com.example.libraryapp.application.bookitemrequest;

import com.example.libraryapp.domain.bookitemrequest.dto.BookItemRequestDto;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequest;

class BookItemRequestMapper {

    static BookItemRequestDto toDto(BookItemRequest model) {
        return new BookItemRequestDto(
                model.getId(),
                model.getCreationDate(),
                model.getStatus(),
                model.getUserId(),
                model.getBookItemId()
        );
    }

    static BookItemRequest toModel(BookItemRequestDto model) {
        return new BookItemRequest(
                model.id(),
                model.creationDate(),
                model.status(),
                model.userId(),
                model.bookItemId()
        );
    }
}
