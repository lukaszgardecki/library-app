package com.example.activityservice.domain.event.incoming;

import com.example.activityservice.domain.model.BookItemId;
import com.example.activityservice.domain.model.RequestId;
import com.example.activityservice.domain.model.Title;
import com.example.activityservice.domain.model.UserId;
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