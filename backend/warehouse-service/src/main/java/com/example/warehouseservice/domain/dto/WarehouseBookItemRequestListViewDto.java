package com.example.warehouseservice.domain.dto;

import com.example.warehouseservice.domain.model.BookFormat;
import com.example.warehouseservice.domain.model.BookItemRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class WarehouseBookItemRequestListViewDto {
    private final Long id;
    private final BookItemRequestStatus status;
    private final LocalDateTime creationDate;
    private final String bookTitle;
    private final String barcode;
    private final BookFormat bookFormat;
    private final String rackName;
    private final String shelfName;
}
