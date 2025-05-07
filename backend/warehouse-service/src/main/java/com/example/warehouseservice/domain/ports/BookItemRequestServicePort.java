package com.example.warehouseservice.domain.ports;

import com.example.warehouseservice.domain.dto.BookItemRequestDto;
import com.example.warehouseservice.domain.model.BookItemRequestStatus;
import com.example.warehouseservice.domain.model.RequestId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookItemRequestServicePort {

    Page<BookItemRequestDto> getPageOfBookRequestsByStatus(BookItemRequestStatus status, Pageable pageable);

    BookItemRequestDto getBookItemRequestById(RequestId requestId);

    void changeBookItemRequestStatus(RequestId requestId, BookItemRequestStatus status);

    void changeBookRequestStatusToReady(RequestId requestId);
}
