package com.example.notificationservice.domain.event.incoming;

import com.example.notificationservice.domain.integration.catalog.BookItemId;
import com.example.notificationservice.domain.integration.request.RequestId;
import com.example.notificationservice.domain.integration.catalog.Title;
import com.example.notificationservice.domain.model.values.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestReadyEvent {
    private UserId userId;
    private BookItemId bookItemId;
    private RequestId requestId;
    private Title bookTitle;
}