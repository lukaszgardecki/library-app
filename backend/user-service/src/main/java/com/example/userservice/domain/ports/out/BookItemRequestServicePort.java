package com.example.userservice.domain.ports.out;

import com.example.userservice.domain.integration.request.BookItemRequest;
import com.example.userservice.domain.model.user.values.UserId;

import java.util.List;

public interface BookItemRequestServicePort {

    void cancelAllItemRequestsByUserId(UserId userId);

    List<BookItemRequest> getUserCurrentBookItemRequests(UserId id);
}
