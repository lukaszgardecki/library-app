package com.example.libraryapp.domain.book.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookPreviewDto extends RepresentationModel<BookPreviewDto> {
    private Long id;
    private String title;
    private String subject;
    private String publisher;
}
