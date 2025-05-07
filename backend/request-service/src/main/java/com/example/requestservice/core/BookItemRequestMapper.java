package com.example.requestservice.core;

import com.example.requestservice.domain.dto.BookItemRequestDto;
import com.example.requestservice.domain.model.*;
import com.example.requestservice.domain.model.values.BookItemId;
import com.example.requestservice.domain.model.values.BookItemRequestCreationDate;
import com.example.requestservice.domain.model.values.RequestId;
import com.example.requestservice.domain.model.values.UserId;

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
