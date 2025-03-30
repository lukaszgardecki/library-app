package com.example.libraryapp.application.warehouse;

import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.domain.warehouse.dto.WarehouseBookItemRequestListViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class WarehouseFacade {
    private final GetBookItemRequestList getBookItemRequestList;

    public Page<WarehouseBookItemRequestListViewDto> getBookItemRequestList(BookItemRequestStatus status, Pageable pageable) {
        return getBookItemRequestList.execute(status, pageable)
                .map(WarehouseMapper::toRequestListViewDto);
    }

}
