package com.example.userservice.infrastructure.integration.bookitemrequestservice;

import com.example.userservice.domain.integration.catalog.BookItemId;
import com.example.userservice.domain.integration.request.BookItemRequest;
import com.example.userservice.domain.integration.request.values.BookItemRequestCreationDate;
import com.example.userservice.domain.integration.request.values.BookItemRequestStatus;
import com.example.userservice.domain.integration.request.values.RequestId;
import com.example.userservice.domain.model.user.values.UserId;
import com.example.userservice.infrastructure.integration.bookitemrequestservice.dto.BookItemRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class BookItemRequestMapper {

    static BookItemRequestDto toDto(BookItemRequest model) {
        return new BookItemRequestDto(
                model.getId().value(),
                model.getCreationDate().value(),
                model.getStatus().name(),
                model.getUserId().value(),
                model.getBookItemId().value()
        );
    }

    static BookItemRequest toModel(BookItemRequestDto model) {
        return new BookItemRequest(
                new RequestId(model.getId()),
                new BookItemRequestCreationDate(model.getCreationDate()),
                BookItemRequestStatus.valueOf(model.getStatus()),
                new UserId(model.getUserId()),
                new BookItemId(model.getBookItemId())
        );
    }
}
