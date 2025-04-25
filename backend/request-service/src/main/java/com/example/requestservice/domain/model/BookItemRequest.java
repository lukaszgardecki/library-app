package com.example.requestservice.domain.model;

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
