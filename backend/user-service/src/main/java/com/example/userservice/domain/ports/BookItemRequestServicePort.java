package com.example.userservice.domain.ports;

import com.example.userservice.domain.dto.user.BookItemRequestDto;
import com.example.userservice.domain.model.user.UserId;

import java.util.List;

public interface BookItemRequestServicePort {

    void cancelAllItemRequestsByUserId(UserId userId);

    List<BookItemRequestDto> getUserCurrentBookItemRequests(UserId id);
}
