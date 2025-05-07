package com.example.activityservice.domain.event.incoming;

import com.example.activityservice.domain.integration.catalog.Title;
import com.example.activityservice.domain.integration.catalog.BookItemId;
import com.example.activityservice.domain.integration.request.BookItemRequestCreationDate;
import com.example.activityservice.domain.integration.request.BookItemRequestStatus;
import com.example.activityservice.domain.integration.request.RequestId;
import com.example.activityservice.domain.model.values.UserId;
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


