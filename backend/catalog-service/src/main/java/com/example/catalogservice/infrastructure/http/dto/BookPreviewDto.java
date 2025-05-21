package com.example.catalogservice.infrastructure.http.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BookPreviewDto {
    private Long id;
    private String title;
    private String subject;
    private String publisher;
}
