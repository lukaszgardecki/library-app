package com.example.userservice.domain.integration.request;

import com.example.userservice.domain.integration.catalog.BookItemId;
import com.example.userservice.domain.integration.request.values.BookItemRequestCreationDate;
import com.example.userservice.domain.integration.request.values.BookItemRequestStatus;
import com.example.userservice.domain.integration.request.values.RequestId;
import com.example.userservice.domain.model.user.values.UserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BookItemRequest {
    private RequestId id;
    private BookItemRequestCreationDate creationDate;
    private BookItemRequestStatus status;
    private UserId userId;
    private BookItemId bookItemId;
}
