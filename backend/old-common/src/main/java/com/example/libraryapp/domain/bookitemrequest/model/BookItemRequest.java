package com.example.libraryapp.domain.bookitemrequest.model;

import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.user.model.UserId;
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
