package com.example.warehouseservice.domain.ports.out;

import com.example.warehouseservice.domain.integration.request.dto.BookItemRequestDto;
import com.example.warehouseservice.domain.integration.request.BookItemRequestStatus;
import com.example.warehouseservice.domain.integration.request.RequestId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookItemRequestServicePort {

    Page<BookItemRequestDto> getPageOfBookRequestsByStatus(BookItemRequestStatus status, Pageable pageable);

    BookItemRequestDto getBookItemRequestById(RequestId requestId);

    void changeBookItemRequestStatus(RequestId requestId, BookItemRequestStatus status);

    void changeBookRequestStatusToReady(RequestId requestId);
}
