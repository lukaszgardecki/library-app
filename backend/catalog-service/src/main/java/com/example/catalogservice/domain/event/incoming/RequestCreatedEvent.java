package com.example.catalogservice.domain.event.incoming;

import com.example.catalogservice.domain.model.BookItemRequestCreationDate;
import com.example.catalogservice.domain.model.BookItemRequestStatus;
import com.example.catalogservice.domain.model.RequestId;
import com.example.catalogservice.domain.model.UserId;
import com.example.catalogservice.domain.model.bookitem.BookItemId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestCreatedEvent {
    private RequestId requestId;
    private BookItemRequestCreationDate requestCreationDate;
    private BookItemRequestStatus requestStatus;
    private BookItemId bookItemId;
    private UserId userId;
}


