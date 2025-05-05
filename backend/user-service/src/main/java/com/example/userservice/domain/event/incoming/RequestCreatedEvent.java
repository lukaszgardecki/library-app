package com.example.userservice.domain.event.incoming;

import com.example.userservice.domain.model.RequestId;
import com.example.userservice.domain.model.bookitem.BookItemId;
import com.example.userservice.domain.model.bookitemrequest.BookItemRequestCreationDate;
import com.example.userservice.domain.model.bookitemrequest.BookItemRequestStatus;
import com.example.userservice.domain.model.user.UserId;
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


