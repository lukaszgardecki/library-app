package com.example.userservice.user.domain.ports;

import com.example.userservice.user.domain.dto.BookItemRequestDto;
import com.example.userservice.user.domain.model.user.UserId;

import java.util.List;

public interface BookItemRequestServicePort {

    void cancelAllItemRequestsByUserId(UserId userId);

    List<BookItemRequestDto> getUserCurrentBookItemRequests(UserId id);
}
