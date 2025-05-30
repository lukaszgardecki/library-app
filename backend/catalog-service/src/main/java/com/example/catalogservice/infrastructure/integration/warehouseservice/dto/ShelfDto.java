package com.example.catalogservice.infrastructure.integration.warehouseservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ShelfDto {
    private Long id;
    private String name;
    private int position;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private int bookItemsCount;
}
