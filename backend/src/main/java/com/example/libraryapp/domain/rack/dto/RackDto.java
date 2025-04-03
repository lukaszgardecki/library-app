package com.example.libraryapp.domain.rack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class RackDto {
    private Long id;
    private String location;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private int  shelvesCount;
}
