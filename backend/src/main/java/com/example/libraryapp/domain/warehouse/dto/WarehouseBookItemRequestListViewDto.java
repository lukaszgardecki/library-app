package com.example.libraryapp.domain.warehouse.dto;

import com.example.libraryapp.domain.bookitem.model.BookItemFormat;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class WarehouseBookItemRequestListViewDto {
    private final Long requestId;
    private final BookItemRequestStatus status;
    private final LocalDateTime creationDate;
    private final String bookTitle;
    private final String barcode;
    private final BookItemFormat bookItemFormat;
    private final String rackLocationId;
}
