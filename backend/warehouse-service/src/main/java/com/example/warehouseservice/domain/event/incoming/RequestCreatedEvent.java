package com.example.warehouseservice.domain.event.incoming;

import com.example.warehouseservice.domain.integration.catalog.BookItemId;
import com.example.warehouseservice.domain.integration.catalog.Title;
import com.example.warehouseservice.domain.integration.request.BookItemRequestCreationDate;
import com.example.warehouseservice.domain.integration.request.BookItemRequestStatus;
import com.example.warehouseservice.domain.integration.request.RequestId;
import com.example.warehouseservice.domain.integration.user.UserId;
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


