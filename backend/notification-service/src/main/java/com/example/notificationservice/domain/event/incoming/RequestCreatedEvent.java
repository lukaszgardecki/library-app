package com.example.notificationservice.domain.event.incoming;

import com.example.notificationservice.domain.integration.catalog.BookItemId;
import com.example.notificationservice.domain.integration.catalog.Title;
import com.example.notificationservice.domain.integration.request.BookItemRequestCreationDate;
import com.example.notificationservice.domain.integration.request.BookItemRequestStatus;
import com.example.notificationservice.domain.integration.request.RequestId;
import com.example.notificationservice.domain.model.values.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCreatedEvent {
    private RequestId requestId;
    private BookItemRequestCreationDate requestCreationDate;
    private BookItemRequestStatus requestStatus;
    private BookItemId bookItemId;
    private UserId userId;
    private Title bookTitle;
}


