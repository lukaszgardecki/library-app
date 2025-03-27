package com.example.libraryapp.application.bookitemrequest;

import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.bookitemrequest.dto.BookItemRequestDto;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestCreationDate;
import com.example.libraryapp.domain.bookitemrequest.model.RequestId;
import com.example.libraryapp.domain.user.model.UserId;

class BookItemRequestMapper {

    static BookItemRequestDto toDto(BookItemRequest model) {
        return new BookItemRequestDto(
                model.getId().value(),
                model.getCreationDate().value(),
                model.getStatus(),
                model.getUserId().value(),
                model.getBookItemId().value()
        );
    }

    static BookItemRequest toModel(BookItemRequestDto model) {
        return new BookItemRequest(
                new RequestId(model.getId()),
                new BookItemRequestCreationDate(model.getCreationDate()),
                model.getStatus(),
                new UserId(model.getUserId()),
                new BookItemId(model.getBookItemId())
        );
    }
}
