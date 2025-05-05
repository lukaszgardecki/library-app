package com.example.notificationservice.domain.event.incoming;

import com.example.notificationservice.domain.model.BookItemId;
import com.example.notificationservice.domain.model.RequestId;
import com.example.notificationservice.domain.model.UserId;
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
}