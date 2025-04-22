package com.example.libraryapp.domain.user.ports;

import com.example.userservice.common.user.model.UserId;

public interface BookItemRequestServicePort {

    void cancelAllItemRequestsByUserId(UserId userId);
}
