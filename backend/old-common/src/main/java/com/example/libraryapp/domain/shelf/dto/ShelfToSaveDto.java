package com.example.libraryapp.domain.shelf.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ShelfToSaveDto {
    private String name;
    private Long rackId;
}
