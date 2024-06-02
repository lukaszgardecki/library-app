package com.example.libraryapp.domain.card.dto;

import com.example.libraryapp.domain.card.CardStatus;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardDto extends RepresentationModel<CardDto> {
    private Long id;
    private String barcode;
    private LocalDateTime issuedAt;
    private CardStatus status;
}
