package com.example.loanservice.domain.ports;

import com.example.loanservice.domain.model.BookItemId;
import com.example.loanservice.domain.model.RequestId;
import com.example.loanservice.domain.model.UserId;

public interface BookItemRequestServicePort {

    RequestId checkIfBookItemRequestStatusIsReady(BookItemId bookItemId, UserId userId);

    void ensureBookItemNotRequested(BookItemId bookItemId);

    Boolean isBookItemRequested(BookItemId bookItemId);
}
