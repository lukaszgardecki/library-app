package com.example.libraryapp.domain.reservation.dto;

import com.example.libraryapp.domain.bookItem.dto.BookItemDto;
import com.example.libraryapp.domain.member.dto.MemberDto;
import com.example.libraryapp.domain.reservation.ReservationStatus;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse extends RepresentationModel<ReservationResponse> {
    private Long id;
    private LocalDateTime creationDate;
    private ReservationStatus status;
    private MemberDto member;
    private BookItemDto bookItem;
}