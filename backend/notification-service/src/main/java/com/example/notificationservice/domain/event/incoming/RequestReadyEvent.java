package com.example.notificationservice.domain.event.incoming;

import com.example.notificationservice.domain.model.BookItemId;
import com.example.notificationservice.domain.model.RequestId;
import com.example.notificationservice.domain.model.UserId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestReadyEvent {
    private UserId userId;
    private BookItemId bookItemId;
    private RequestId requestId;
}