package com.example.warehouseservice.domain.event.incoming;

import com.example.warehouseservice.domain.model.*;
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
}


