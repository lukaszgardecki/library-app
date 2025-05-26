package com.example.loanservice.domain.ports.out;

import com.example.loanservice.domain.integration.requestservice.RequestId;
import com.example.loanservice.domain.model.values.BookItemId;
import com.example.loanservice.domain.model.values.UserId;

public interface BookItemRequestServicePort {

    RequestId checkIfBookItemRequestStatusIsReady(BookItemId bookItemId, UserId userId);

    void ensureBookItemNotRequested(BookItemId bookItemId);

    Boolean isBookItemRequested(BookItemId bookItemId);
}
