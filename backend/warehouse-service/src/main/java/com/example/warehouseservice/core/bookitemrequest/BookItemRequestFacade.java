package com.example.warehouseservice.core.bookitemrequest;

import com.example.warehouseservice.domain.dto.BookItemRequestDto;
import com.example.warehouseservice.domain.dto.WarehouseBookItemRequestListViewDto;
import com.example.warehouseservice.domain.model.BookItemRequestStatus;
import com.example.warehouseservice.domain.model.RequestId;
import com.example.warehouseservice.domain.model.WarehouseBookItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class BookItemRequestFacade {
    private final GetBookItemRequestList getBookItemRequestList;
    private final GetBookItemRequest getBookItemRequest;
    private final ChangeBookItemRequestStatusToReadyUseCase changeBookItemRequestStatusToReadyUseCase;

    public Page<WarehouseBookItemRequestListViewDto> getBookItemRequestList(BookItemRequestStatus status, Pageable pageable) {
        return getBookItemRequestList.execute(status, pageable)
                .map(BookItemRequestMapper::toRequestListViewDto);
    }

    public WarehouseBookItemRequestListViewDto getBookItemRequest(BookItemRequestDto request) {
        WarehouseBookItemRequest warehouseBookItemRequest = getBookItemRequest.execute(request);
        return BookItemRequestMapper.toRequestListViewDto(warehouseBookItemRequest);
    }

    public void changeBookRequestStatusToReady(RequestId requestId) {
        changeBookItemRequestStatusToReadyUseCase.execute(requestId);
    }
}
